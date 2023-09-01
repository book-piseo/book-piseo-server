package com.bookpiseo.dto

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode

data class CommonResponse (
        var statusCode: HttpStatusCode,
        var message: String
) {
    companion object {
        fun ok() : CommonResponse {
            return CommonResponse(
                    statusCode = HttpStatus.OK,
                    message = "성공"
            )
        }
    }
}