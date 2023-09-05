package com.bookpiseo.controller

import com.bookpiseo.dto.ContentsInfo
import com.bookpiseo.dto.TeamInfo
import com.bookpiseo.service.ContentsService
import com.bookpiseo.service.TeamService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpSession
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "팀 상세 화면", description = "팀에 관련 된 API를 다룹니다.")
@RestController
@RequestMapping("/api/team")
class TeamController(
        val contentsService: ContentsService,
        val teamService: TeamService
) {

    @Operation(
            summary = "팀 페이지 정보 API", description = """
팀 페이지 정보 API
		
#### 사용자 정의 예외 케이스
| Http Status | Error Code  | Error Message | Error Data | Remark   |
|-------------|-------------|--------------|------------|-----------|
|     403     | SESSION_EXPIRED | 세션이 만료 되었습니다. | | |   
"""
    )
    @GetMapping("/{teamId}")
    fun getTeamDetailInfo(
            @Parameter(description = "유저 토큰") @RequestHeader(name = "User-Token") userToken: String,
            @Parameter(description = "Team ID") @PathVariable teamId: String
    ): ResponseEntity<TeamInfo.TeamDetailInfoResponse> {
        return ResponseEntity.ok(teamService.getTeamDetailInfo(userToken, teamId))
    }

    @Operation(
            summary = "팀 상세 화면 > 소속 팀의 게시물 정보 API", description = """
팀 상세 화면 > 소속 팀의 게시물 정보 가져오기 API
		
#### 사용자 정의 예외 케이스
| Http Status | Error Code  | Error Message | Error Data | Remark   |
|-------------|-------------|--------------|------------|-----------|
|     403     | SESSION_EXPIRED | 세션이 만료 되었습니다. | | |   
"""
    )
    @GetMapping("/{teamId}/{pageNumber}")
    fun getTeamContentsInfos(
            @Parameter(description = "유저 토큰") @RequestHeader(name = "User-Token") userToken: String,
            @Parameter(description = "팀 ID") @PathVariable teamId: String,
            @Parameter(description = "페이징 넘버, 0부터 시작") @PathVariable pageNumber: Int
    ): ResponseEntity<Page<ContentsInfo.ContentsInfoResponse>> {
        return ResponseEntity.ok(contentsService.getTeamContentsInfos(userToken, teamId, pageNumber))
    }


}