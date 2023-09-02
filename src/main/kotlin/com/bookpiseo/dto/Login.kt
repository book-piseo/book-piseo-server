package com.bookpiseo.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

class Login {
    @Schema(description = "로그인 Request 객체")
    data class LoginRequest(
            @Email
            @Schema(description = "이메일", required = true)
            var email: String,
            @NotBlank
            @Schema(description = "비밀번호", required = true)
            var password: String
    ) {

    }
}