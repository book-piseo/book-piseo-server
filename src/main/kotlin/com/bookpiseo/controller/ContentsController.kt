package com.bookpiseo.controller

import com.bookpiseo.dto.ContentsInfo
import com.bookpiseo.service.ContentsService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "게시물", description = "게시물에 관련 된 API를 다룹니다.")
@RestController
@RequestMapping("/api/contents")
class ContentsController(
        val contentsService: ContentsService
) {

    @Operation(
            summary = "게시물 작성 API", description = """
게시물 작성 API
		
#### 사용자 정의 예외 케이스
| Http Status | Error Code  | Error Message | Error Data | Remark   |
|-------------|-------------|--------------|------------|-----------|
|     403     | SESSION_EXPIRED | 세션이 만료 되었습니다. | | |   
"""
    )
    @PostMapping
    fun saveContentsInfo(
            @Parameter(description = "유저 토큰") @RequestHeader(name = "User-Token") userToken: String,
            @Parameter(description = "작성 게시물 정보") @RequestBody @Valid request: ContentsInfo.ContentsSaveRequest
    ): ResponseEntity<ContentsInfo.ContentsSaveResponse> {
        return ResponseEntity.ok(contentsService.saveContents(userToken = userToken, request = request))
    }

    @Operation(
            summary = "게시물 상세 API", description = """
게시물 상세 API
		
#### 사용자 정의 예외 케이스
| Http Status | Error Code  | Error Message | Error Data | Remark   |
|-------------|-------------|--------------|------------|-----------|
|     400     | INVALID_PARAMETER | 잘못 된 파라미터 정보입니다. | | |   
"""
    )
    @GetMapping("/{contentsId}")
    fun getContentsInfo(
            @Parameter(description = "게시물 ID", required = true) @PathVariable contentsId: String
    ): ResponseEntity<ContentsInfo.ContentsInfoResponse> {

        return ResponseEntity.ok(contentsService.getContentsInfo(contentsId = contentsId))
    }

}