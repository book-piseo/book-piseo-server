package com.bookpiseo.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "도서 정보 (네이버 도서 검색 API Response 기반)")
data class BookInfo(
        @Schema(description = "도서 제목")
        val title: String?,
        @Schema(description = "도서 링크")
        val link: String?,
        @Schema(description = "도서 썸네일 이미지")
        val image: String?,
        @Schema(description = "도서 저자")
        val author: String?,
        @Schema(description = "도서 판매 가격")
        val discount: String?,
        @Schema(description = "출판사")
        val publisher: String?,
        @Schema(description = "출간일")
        val pubdate: String?,
        @Schema(description = "ISBN")
        val isbn: String?,
        @Schema(description = "도서 설명")
        val description: String?
)

