package com.bookpiseo.service


import com.bookpiseo.dto.BookInfo
import com.bookpiseo.dto.ContentsInfo
import com.bookpiseo.dto.UserInfo
import com.bookpiseo.entity.BookPiseoContents
import com.bookpiseo.exception.BaseException
import com.bookpiseo.exception.BaseResponseCode
import com.bookpiseo.repository.BookPiseoContentsRepository
import com.bookpiseo.repository.BookPiseoTeamRepository
import com.bookpiseo.repository.BookPiseoUserRepository
import com.google.gson.Gson
import jakarta.servlet.http.HttpSession
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.ZoneId


@Service
class ContentsService(
        val bookPiseoContentsRepository: BookPiseoContentsRepository,
        val bookPiseoTeamRepository: BookPiseoTeamRepository,
        val bookPiseoUserRepository: BookPiseoUserRepository
) {
    private final val PAGE_SIZE: Int = 20

    @Transactional(rollbackFor = [Exception::class])
    fun saveContents(
            userInfo: UserInfo.UserSessionInfo,
            request: ContentsInfo.ContentsSaveRequest) {

        bookPiseoContentsRepository.save(
                BookPiseoContents(
                        contentsTitle = request.contentsTitle,
                        contentsText = request.contentsText,
                        bookInfo = Gson().toJson(request.bookInfo),
                        teamId = request.teamId,
                        writerId = userInfo.userId,
                )
        )
    }


    @Transactional(rollbackFor = [Exception::class])
    fun getOtherTeamsContentsInfos(httpSession: HttpSession,
                                   pageNumber: Int
    ): Page<ContentsInfo.ContentsInfoResponse> {
        val userSessionInfo = httpSession.getAttribute("user")?.let { user ->
            user as UserInfo.UserSessionInfo
        }
        userSessionInfo ?: throw BaseException(BaseResponseCode.SESSION_EXPIRED)

        val pageable: PageRequest = PageRequest.of(pageNumber, PAGE_SIZE)

        val otherTeamsContentsInfos = toContentsInfo(userSessionInfo.affiliatedTeamInfos?.let {
            bookPiseoContentsRepository.findAll(pageable)
        })

        return otherTeamsContentsInfos ?: Page.empty()
    }

    @Transactional(rollbackFor = [Exception::class])
    fun getAffiliatedTeamsContentsInfos(httpSession: HttpSession,
                                        pageNumber: Int
    ): Page<ContentsInfo.ContentsInfoResponse> {
        val userSessionInfo = httpSession.getAttribute("user")?.let { user ->
            user as UserInfo.UserSessionInfo
        }
        userSessionInfo ?: throw BaseException(BaseResponseCode.SESSION_EXPIRED)
        val pageable: PageRequest = PageRequest.of(pageNumber, PAGE_SIZE)
        val affiliatedTeamsContentsInfos = toContentsInfo(
                userSessionInfo.affiliatedTeamInfos
                        ?.let {
                            bookPiseoContentsRepository.findAllByTeamIdIn(
                                    it.map { teamInfo -> teamInfo.teamId },
                                    pageable = pageable)
                        }
        )

        return affiliatedTeamsContentsInfos ?: Page.empty()

    }

    @Transactional(rollbackFor = [Exception::class])
    fun getTeamContentsInfos(httpSession: HttpSession,
                             teamId: String,
                             pageNumber: Int
    ): Page<ContentsInfo.ContentsInfoResponse> {
        val userSessionInfo = httpSession.getAttribute("user")?.let { user ->
            user as UserInfo.UserSessionInfo
        }
        userSessionInfo ?: throw BaseException(BaseResponseCode.SESSION_EXPIRED)
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
                        bookInfo = Gson().fromJson(contentsInfo.bookInfo, BookInfo::class.java),
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


}