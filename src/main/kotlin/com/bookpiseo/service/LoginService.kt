package com.bookpiseo.service


import com.bookpiseo.dto.Login
import com.bookpiseo.dto.UserInfo
import com.bookpiseo.exception.BaseException
import com.bookpiseo.exception.BaseResponseCode
import com.bookpiseo.repository.BookPiseoAffiliatedTeamRepository
import com.bookpiseo.repository.BookPiseoUserRepository
import jakarta.servlet.http.HttpSession
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LoginService(
        val bookPiseoUserRepository: BookPiseoUserRepository,
        val bookPiseoAffiliatedTeamRepository: BookPiseoAffiliatedTeamRepository
) {

    @Transactional(rollbackFor = [Exception::class])
    fun login(
            httpSession: HttpSession,
            request: Login.LoginRequest
    ) {
        val user = bookPiseoUserRepository.findByEmail(request.email)
        val encoder = BCryptPasswordEncoder()
        if (encoder.matches(request.password, user.password)) {
            val affiliatedTeams = bookPiseoAffiliatedTeamRepository.findAllByUserId(user.userId!!)
            // 로그인 성공
            httpSession.setAttribute("user", UserInfo.UserSessionInfo(
                    userId = user.userId!!,
                    userName = user.userName,
                    profileImg = user.profileImg,
                    email = user.email,
                    phone = user.phone,
                    affiliatedTeamInfos = affiliatedTeams.map { team ->
                        UserInfo.AffiliatedTeamInfo(
                                teamId = team.bookPiseoTeam.teamId!!,
                                teamName = team.bookPiseoTeam.teamName,
                                teamDescription = team.bookPiseoTeam.teamDescription,
                                teamImg = team.bookPiseoTeam.teamImg,
                                isMaster = team.isMaster,
                        )
                    }
            ))
            return
        }

        // 로그인 실패
        throw BaseException(BaseResponseCode.INVALID_LOGIN_INFO)
    }
}