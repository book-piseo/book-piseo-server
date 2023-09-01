package com.bookpiseo.controller

import com.bookpiseo.dto.CommonResponse
import com.bookpiseo.dto.LoginRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController()
@RequestMapping("/api")
class LoginController {

    @PostMapping("/login")
    fun login(
            @RequestBody request: LoginRequest
    ) : ResponseEntity<CommonResponse>{

        return ResponseEntity.ok(CommonResponse.ok())
    }

}