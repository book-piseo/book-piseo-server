package com.bookpiseo.controller

import com.bookpiseo.dto.ContentsInfo
import com.bookpiseo.service.ContentsService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpSession
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "홈 화면", description = "홈 화면에 관련 된 API를 다룹니다.")
@RestController
@RequestMapping("/api/home")
class HomeController(
        val contentsService: ContentsService
) {

    @Operation(
            summary = "홈 화면 > 다른 팀들의 게시물 정보 API", description = """
홈 화면 > 다른 팀들의 게시물 정보 가져오기 API
		
#### 사용자 정의 예외 케이스
| Http Status | Error Code  | Error Message | Error Data | Remark   |
|-------------|-------------|--------------|------------|-----------|
|     403     | SESSION_EXPIRED | 세션이 만료 되었습니다. | | |   
"""
    )
    @GetMapping("/other-teams/contents/{pageNumber}")
    fun getOtherTeamsContentsInfos(
            @Parameter(description = "페이징 넘버, 0부터 시작") @PathVariable pageNumber: Int
    ): ResponseEntity<Page<ContentsInfo.ContentsInfoResponse>> {
        return ResponseEntity.ok(contentsService.getOtherTeamsContentsInfos(pageNumber))
    }

    @Operation(
            summary = "홈 화면 > 소속 된 팀들의 게시물 정보 API", description = """
홈 화면 > 소속 된 팀들의 게시물 정보 가져오기 API
		
#### 사용자 정의 예외 케이스
| Http Status | Error Code  | Error Message | Error Data | Remark   |
|-------------|-------------|--------------|------------|-----------|
|     403     | SESSION_EXPIRED | 세션이 만료 되었습니다. | | |   
"""
    )
    @GetMapping("/affiliated-teams/contents/{pageNumber}")
    fun getAffiliatedTeamsContentsInfos(
            @Parameter(description = "유저 토큰") @RequestHeader(name = "User-Token") userToken: String,
            @Parameter(description = "페이징 넘버, 0부터 시작") @PathVariable pageNumber: Int
    ): ResponseEntity<Page<ContentsInfo.ContentsInfoResponse>> {
        return ResponseEntity.ok(contentsService.getAffiliatedTeamsContentsInfos(userToken, pageNumber))
    }


}