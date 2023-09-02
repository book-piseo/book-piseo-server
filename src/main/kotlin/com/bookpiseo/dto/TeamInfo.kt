package com.bookpiseo.dto

import io.swagger.v3.oas.annotations.media.Schema

class TeamInfo {

    @Schema(description = "팀에 소속 된 멤버 정보")
    data class TeamMemberInfoResponse(
            @Schema(description = "팀 소속 멤버 유저 ID", required = true)
            val userId: String,
            @Schema(description = "팀 소속 멤버 이름", required = true)
            val userName: String,
            @Schema(description = "팀 소속 멤버 프로필 이미지", required = false)
            val profileImg: String?,
            @Schema(description = "팀 소속 멤버 이메일", required = true)
            val email: String,
            @Schema(description = "팀 소속 멤버 전화번호", required = false)
            val phone: String?,
            @Schema(description = "팀 소속 멤버의 마스터 권한 여부", required = true)
            val isMaster: Boolean? = false,
    )

    @Schema(description = "팀 상세 정보")
    data class TeamDetailInfoResponse(
            @Schema(description = "팀 ID", required = true)
            val teamId: String,
            @Schema(description = "팀 이름", required = true)
            val teamName: String,
            @Schema(description = "팀 소개", required = false)
            val teamDescription: String?,
            @Schema(description = "팀 프로필 이미지", required = false)
            val teamImg: String?,
            @Schema(description = "팀에 대한 세션 유저의 마스터 권한 여부", required = true)
            val isMaster: Boolean? = false,
            @Schema(description = "팀 소속 멤버들 정보", required = true)
            val teamMembers: List<TeamMemberInfoResponse>? = emptyList(),
    )
}