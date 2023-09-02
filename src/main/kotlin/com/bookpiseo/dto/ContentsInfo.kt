package com.bookpiseo.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

class ContentsInfo {
    @Schema(description = "작성 게시물 저장 Request")
    data class ContentsSaveRequest(
            @NotBlank
            @Schema(description = "게시물 제목", required = true)
            val contentsTitle: String,
            @Schema(description = "게시물 내용", required = false)
            val contentsText: String?,
            @NotNull
            @Schema(description = "도서 정보", required = true)
            val bookInfo: BookInfo,
            @NotBlank
            @Schema(description = "해당 게시물을 게시 할 팀의 ID", required = true)
            val teamId: String
    )

    @Schema(description = "게시물 정보")
    data class ContentsInfoResponse(
            @Schema(description = "게시물 ID", required = true)
            val contentsId: String,
            @Schema(description = "게시물 제목", required = true)
            val contentsTitle: String,
            @Schema(description = "게시물 본문", required = false)
            val contentsText: String?,
            @Schema(description = "도서 정보", required = true)
            val bookInfo: BookInfo,
            @Schema(description = "팀 ID", required = true)
            val teamId: String,
            @Schema(description = "팀 이름", required = true)
            val teamName: String,
            @Schema(description = "작성자 정보", required = false)
            val writerInfo: WriterInfoResponse?,
            @Schema(description = "작성일", required = true)
            val regDt: LocalDateTime,
    )

    @Schema(description = "작성자 정보")
    data class WriterInfoResponse(
            @Schema(description = "작성자 ID")
            val userId: String,
            @Schema(description = "작성자 이름")
            val userName: String,
            @Schema(description = "작성자 프로필 사진")
            val profileImg: String?,
            @Schema(description = "작성자 이메일")
            val email: String,
            @Schema(description = "작성자 전화번호")
            val phone: String?,
    )
}