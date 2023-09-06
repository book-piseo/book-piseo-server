package com.bookpiseo.controller

import com.bookpiseo.dto.ErrorResponse
import com.bookpiseo.dto.Login
import com.bookpiseo.service.LoginService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpSession
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "로그인", description = "로그인에 관련 된 API를 다룹니다.")
@RestController
@RequestMapping("/api")
class LoginController(
        val loginService: LoginService
) {

    @Operation(
            summary = "Login API", description = """
로그인 API
		
#### 사용자 정의 예외 케이스
| Http Status | Error Code  | Error Message | Error Data | Remark   |
|-------------|-------------|--------------|------------|-----------|
|     401     | INVALID_LOGIN_INFO | 아이디 또는 패스워드가 틀렸습니다. | | |   
""",
            responses = [
                ApiResponse(responseCode = "200", description = "성공"),
                ApiResponse(responseCode = "401", description = "아이디 또는 패스워드가 틀렸습니다.", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
                ApiResponse(responseCode = "500", description = "서버 에러", content = [Content(schema = Schema(implementation = ErrorResponse::class))])
            ])
    @PostMapping("/login")
    fun login(
            @Parameter(description = "로그인 요청 객체") @RequestBody @Valid request: Login.LoginRequest
    ): ResponseEntity<Login.LoginResponse> {
        return ResponseEntity.ok(loginService.login(request = request))
    }

}