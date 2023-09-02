package com.bookpiseo.service


import com.bookpiseo.dto.TeamInfo
import com.bookpiseo.dto.UserInfo
import com.bookpiseo.exception.BaseException
import com.bookpiseo.exception.BaseResponseCode
import com.bookpiseo.repository.BookPiseoAffiliatedTeamRepository
import com.bookpiseo.repository.BookPiseoTeamRepository
import com.bookpiseo.repository.BookPiseoUserRepository
import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class TeamService(
        val bookPiseoAffiliatedTeamRepository: BookPiseoAffiliatedTeamRepository,
        val bookPiseoTeamRepository: BookPiseoTeamRepository,
        val bookPiseoUserRepository: BookPiseoUserRepository
) {

    @Transactional(rollbackFor = [Exception::class])
    fun getTeamDetailInfo(httpSession: HttpSession, teamId: String): TeamInfo.TeamDetailInfoResponse {
        val userSessionInfo = httpSession.getAttribute("user")?.let { user ->
            user as UserInfo.UserSessionInfo
        }
        userSessionInfo ?: throw BaseException(BaseResponseCode.SESSION_EXPIRED)

        return bookPiseoTeamRepository.findById(teamId).orElse(null)?.let {
            val teamMembers = bookPiseoAffiliatedTeamRepository.findAllByBookPiseoTeam(it)
            val teamMemberInfos = bookPiseoUserRepository.findAllById(teamMembers.map { teamMember -> teamMember.userId })
            TeamInfo.TeamDetailInfoResponse(
                    teamId = it.teamId!!,
                    teamName = it.teamName,
                    teamDescription = it.teamDescription,
                    teamImg = it.teamImg,
                    isMaster = userSessionInfo.affiliatedTeamInfos?.any { affiliatedTeamInfo ->
                        affiliatedTeamInfo.teamId == it.teamId && affiliatedTeamInfo.isMaster == true
                    },
                    teamMembers = teamMemberInfos.mapNotNull { teamMemberInfo ->
                        teamMemberInfo?.let {
                            TeamInfo.TeamMemberInfoResponse(
                                    userId = teamMemberInfo.userId!!,
                                    userName = teamMemberInfo.userName,
                                    profileImg = teamMemberInfo.profileImg,
                                    email = teamMemberInfo.email,
                                    phone = teamMemberInfo.phone,
                                    isMaster = teamMembers.any { teamMember ->
                                        teamMember.userId == teamMemberInfo.userId && teamMember.isMaster
                                    }
                            )
                        }

                    }
            )
        } ?: throw BaseException(BaseResponseCode.INTERNAL_SERVER_ERROR)
    }


}