package com.bookpiseo.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "공통 Error Response Model")
data class ErrorResponse(
        @Schema(description = "HttpStatusCode가 200이 아닐 경우 코드를 반환.", nullable = true)
        var errorCode: String?,
        @Schema(description = "HttpStatusCode가 200이 아닐 경우 메세지 반환.", nullable = true)
        var errorMessage: String?
) {
    companion object {
        fun error(status: String, errorCode: String, errorMessage: String): ErrorResponse {
            return ErrorResponse(
                    errorCode = errorCode,
                    errorMessage = errorMessage
            )
        }
    }
}
