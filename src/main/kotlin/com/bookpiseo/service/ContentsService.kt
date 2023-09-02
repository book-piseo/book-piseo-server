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
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.ZoneId


@Service
class ContentsService(
        val bookPiseoContentsRepository: BookPiseoContentsRepository,
        val bookPiseoTeamRepository: BookPiseoTeamRepository,
        val bookPiseoUserRepository: BookPiseoUserRepository
) {
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
    fun getHomeContentsInfos(httpSession: HttpSession): ContentsInfo.HomeContentsInfo {
        val userSessionInfo = httpSession.getAttribute("user")?.let { user ->
            user as UserInfo.UserSessionInfo
        }
        userSessionInfo ?: throw BaseException(BaseResponseCode.SESSION_EXPIRED)

        val teamContentsInfos = toContentsInfo(userSessionInfo.affiliatedTeamInfos?.let { bookPiseoContentsRepository.findAllByTeamIdIn(it.map { teamInfo -> teamInfo.teamId }) })

        val otherContentsInfos = toContentsInfo(userSessionInfo.affiliatedTeamInfos?.let { bookPiseoContentsRepository.findAllByTeamId(it[0].teamId) })

        return ContentsInfo.HomeContentsInfo(
                teamContentsInfos = teamContentsInfos,
                otherContentsInfos = otherContentsInfos
        )
    }

    fun toContentsInfo(bookPiseoContentsInfos: List<BookPiseoContents>?): List<ContentsInfo.ContentsInfoResponse>? {
        return bookPiseoContentsInfos?.map { contentsInfo ->
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
    }


}