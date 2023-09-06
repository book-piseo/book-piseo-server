package com.bookpiseo.dto

import io.swagger.v3.oas.annotations.media.Schema

class UserInfo {
    @Schema(description = "기본 유저 정보")
    data class UserDefaultInfo(
            @Schema(description = "유저 ID")
            val userId: String,
            @Schema(description = "유저 이름")
            val userName: String,
            @Schema(description = "유저 프로필 이미지")
            val profileImg: String?,
            @Schema(description = "유저 이메일")
            val email: String,
            @Schema(description = "유저 전화번호")
            val phone: String?,
            @Schema(description = "유저 소속 팀 정보")
            val affiliatedTeamInfos: List<AffiliatedTeamInfo>? = emptyList()
    )

    @Schema(description = "유저가 소속 된 팀 요약 정보")
    data class AffiliatedTeamInfo(
            @Schema(description = "팀 ID", required = true)
            val teamId: String,
            @Schema(description = "팀 이름", required = true)
            val teamName: String,
            @Schema(description = "팀 소개", required = false)
            val teamDescription: String?,
            @Schema(description = "팀 프로필 이미지", required = false)
            val teamImg: String?,
            @Schema(description = "팀에 대한 마스터 권한 여부", required = true)
            val isMaster: Boolean? = false
    )
}