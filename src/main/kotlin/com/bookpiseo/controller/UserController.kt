package com.bookpiseo.controller

import com.bookpiseo.dto.UserInfo
import com.bookpiseo.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpSession
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "유저 정보", description = "유저 정보에 관련 된 API를 다룹니다.")
@RestController
@RequestMapping("/api/user")
class UserController(
        val userService: UserService
) {

    @Operation(
            summary = "User Info API", description = """
유저 정보 가져오기 API
		
#### 사용자 정의 예외 케이스
| Http Status | Error Code  | Error Message | Error Data | Remark   |
|-------------|-------------|--------------|------------|-----------|
|     403     | SESSION_EXPIRED | 세션이 만료 되었습니다. | | |   
"""
    )
    @GetMapping
    fun getUserInfo(
            @Parameter(description = "유저 토큰") @RequestHeader(name = "User-Token") userToken: String
    ): ResponseEntity<UserInfo.UserDefaultInfo> {
        return ResponseEntity.ok(userService.getUserDefaultInfo(userToken))
    }

}