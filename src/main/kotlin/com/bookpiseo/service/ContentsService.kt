package com.bookpiseo.service


import com.bookpiseo.dto.BookInfo
import com.bookpiseo.dto.ContentsInfo
import com.bookpiseo.entity.BookPiseoContents
import com.bookpiseo.exception.BaseException
import com.bookpiseo.exception.BaseResponseCode
import com.bookpiseo.repository.BookPiseoContentsRepository
import com.bookpiseo.repository.BookPiseoTeamRepository
import com.bookpiseo.repository.BookPiseoUserRepository
import com.bookpiseo.repository.BookPiseoUserTokenRepository
import com.google.gson.Gson
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.ZoneId


@Service
class ContentsService(
        val bookPiseoUserTokenRepository: BookPiseoUserTokenRepository,
        val bookPiseoContentsRepository: BookPiseoContentsRepository,
        val bookPiseoTeamRepository: BookPiseoTeamRepository,
        val bookPiseoUserRepository: BookPiseoUserRepository,
        val userService: UserService
) {
    private final val PAGE_SIZE: Int = 20

    @Transactional(rollbackFor = [Exception::class])
    fun saveContents(userToken: String, request: ContentsInfo.ContentsSaveRequest): ContentsInfo.ContentsSaveResponse {
        val userInfo = bookPiseoUserTokenRepository.findById(userToken).orElseThrow {
            BaseException(BaseResponseCode.SESSION_EXPIRED)
        }
        val contentsInfo = bookPiseoContentsRepository.saveAndFlush(
                BookPiseoContents(
                        contentsTitle = request.contentsTitle,
                        contentsText = request.contentsText,
                        bookInfo = request.bookInfo,
                        teamId = request.teamId,
                        writerId = userInfo!!.userId,
                )
        )
        return ContentsInfo.ContentsSaveResponse(contentsId = contentsInfo.contentsId!!)
    }


    @Transactional(rollbackFor = [Exception::class])
    fun getOtherTeamsContentsInfos(
            pageNumber: Int
    ): Page<ContentsInfo.ContentsInfoResponse> {
        val pageable: PageRequest = PageRequest.of(pageNumber, PAGE_SIZE)

        val otherTeamsContentsInfos = toContentsInfo(bookPiseoContentsRepository.findAll(pageable))


        return otherTeamsContentsInfos ?: Page.empty()
    }

    @Transactional(rollbackFor = [Exception::class])
    fun getAffiliatedTeamsContentsInfos(userToken: String, pageNumber: Int): Page<ContentsInfo.ContentsInfoResponse> {
        val userInfo = bookPiseoUserTokenRepository.findById(userToken).orElseThrow {
            BaseException(BaseResponseCode.SESSION_EXPIRED)
        }
        val pageable: PageRequest = PageRequest.of(pageNumber, PAGE_SIZE)
        val affiliatedTeamsContentsInfos = toContentsInfo(
                userService.getUserAffiliatedTeamInfos(userInfo?.userId!!)
                        .let {
                            bookPiseoContentsRepository.findAllByTeamIdIn(
                                    it.map { teamInfo -> teamInfo.teamId },
                                    pageable = pageable)
                        }
        )

        return affiliatedTeamsContentsInfos ?: Page.empty()

    }

    @Transactional(rollbackFor = [Exception::class])
    fun getTeamContentsInfos(
            userToken: String,
            teamId: String,
            pageNumber: Int
    ): Page<ContentsInfo.ContentsInfoResponse> {
        val pageable: PageRequest = PageRequest.of(pageNumber, PAGE_SIZE)
        val teamContentsInfos = toContentsInfo(
                bookPiseoContentsRepository.findAllByTeamId(
                        teamId,
                        pageable = pageable)

        )

        return teamContentsInfos ?: Page.empty()

    }

    fun toContentsInfo(bookPiseoContentsInfos: Page<BookPiseoContents?>?): Page<ContentsInfo.ContentsInfoResponse>? {
        return bookPiseoContentsInfos?.mapNotNull { contentsNullableInfo ->
            contentsNullableInfo?.let { contentsInfo ->
                val writerInfo = contentsInfo.writerId
                        ?.let { bookPiseoUserRepository.findById(it).orElse(null) }
                        ?.let { writerInfo ->
                            ContentsInfo.WriterInfoResponse(
                                    userId = writerInfo.userId!!,
                                    userName = writerInfo.userName,
                                    profileImg = writerInfo.profileImg,
                                    email = writerInfo.email,
                                    phone = writerInfo.phone
                            )
                        }

                ContentsInfo.ContentsInfoResponse(
                        contentsId = contentsInfo.contentsId!!,
                        contentsTitle = contentsInfo.contentsTitle,
                        contentsText = contentsInfo.contentsText,
                        bookInfo = contentsInfo.bookInfo,
                        teamId = contentsInfo.teamId,
                        teamName = bookPiseoTeamRepository.findById(contentsInfo.teamId).orElse(null)?.teamName
                                ?: "Unknown",
                        writerInfo = writerInfo,
                        regDt = contentsInfo.regDt!!.withZoneSameInstant(ZoneId.of("Asia/Seoul")).toLocalDateTime()
                )
            }
        }?.let { transformedList ->
            PageImpl(transformedList, bookPiseoContentsInfos.pageable, bookPiseoContentsInfos.totalElements)
        }
    }

    @Transactional(readOnly = true)
    fun getContentsInfo(contentsId: String): ContentsInfo.ContentsInfoResponse? {
        return bookPiseoContentsRepository.findById(contentsId).orElseThrow {
            BaseException(BaseResponseCode.INVALID_PARAMETER)
        }?.let {
            val writerInfo = it.writerId
                    ?.let { id -> bookPiseoUserRepository.findById(id).orElse(null) }
                    ?.let { writerInfo ->
                        ContentsInfo.WriterInfoResponse(
                                userId = writerInfo.userId!!,
                                userName = writerInfo.userName,
                                profileImg = writerInfo.profileImg,
                                email = writerInfo.email,
                                phone = writerInfo.phone
                        )
                    }
            ContentsInfo.ContentsInfoResponse(
                    contentsId = it.contentsId!!,
                    contentsTitle = it.contentsTitle,
                    contentsText = it.contentsText,
                    bookInfo = Gson().fromJson(Gson().toJson(it.bookInfo), BookInfo::class.java),
                    teamId = it.teamId,
                    teamName = bookPiseoTeamRepository.findById(it.teamId).orElse(null)?.teamName
                            ?: "Unknown",
                    writerInfo = writerInfo,
                    regDt = it.regDt!!.withZoneSameInstant(ZoneId.of("Asia/Seoul")).toLocalDateTime()
            )
        }

    }


}