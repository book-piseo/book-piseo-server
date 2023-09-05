package com.bookpiseo.service


import com.bookpiseo.dto.UserInfo
import com.bookpiseo.exception.BaseException
import com.bookpiseo.exception.BaseResponseCode
import com.bookpiseo.repository.BookPiseoAffiliatedTeamRepository
import com.bookpiseo.repository.BookPiseoUserRepository
import com.bookpiseo.repository.BookPiseoUserTokenRepository
import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
        private val bookPiseoUserRepository: BookPiseoUserRepository,
        private val bookPiseoUserTokenRepository: BookPiseoUserTokenRepository,
        private val bookPiseoAffiliatedTeamRepository: BookPiseoAffiliatedTeamRepository
) {
    @Transactional(readOnly = true)
    fun getUserDefaultInfo(userToken: String): UserInfo.UserDefaultInfo {
        val user = bookPiseoUserRepository.findById(bookPiseoUserTokenRepository.findById(userToken).orElseThrow { BaseException(BaseResponseCode.SESSION_EXPIRED) }?.userId!!).orElseThrow {
            BaseException(BaseResponseCode.SESSION_EXPIRED)

        }
        return UserInfo.UserDefaultInfo(
                userId = user?.userId!!,
                userName = user.userName,
                profileImg = user.profileImg,
                email = user.email,
                phone = user.phone,
                affiliatedTeamInfos = getUserAffiliatedTeamInfos(user.userId!!)
        )
    }

    @Transactional(readOnly = true)
    fun validateUserToken(userToken: String) {
        bookPiseoUserTokenRepository.findById(userToken)
                .orElseThrow {
                    BaseException(BaseResponseCode.SESSION_EXPIRED)
                }
    }

    @Transactional(readOnly = true)
    fun getUserAffiliatedTeamInfos(userId: String): List<UserInfo.AffiliatedTeamInfo> {
        val affiliatedTeams = bookPiseoAffiliatedTeamRepository.findAllByUserId(userId)

        return affiliatedTeams.map { team ->
            UserInfo.AffiliatedTeamInfo(
                    teamId = team.bookPiseoTeam.teamId!!,
                    teamName = team.bookPiseoTeam.teamName,
                    teamDescription = team.bookPiseoTeam.teamDescription,
                    teamImg = team.bookPiseoTeam.teamImg,
                    isMaster = team.isMaster,
            )
        }
    }


}