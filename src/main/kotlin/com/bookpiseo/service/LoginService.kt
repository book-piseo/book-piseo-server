package com.bookpiseo.service

import com.bookpiseo.dto.CommonResponse
import com.bookpiseo.dto.LoginRequest
import com.bookpiseo.repository.BookPiseoUserRepository
import jakarta.servlet.http.HttpSession
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.Exception

@Service
class LoginService(
        val bookPiseoUserRepository: BookPiseoUserRepository
) {

    @Transactional(rollbackFor = [Exception::class])
    fun login(
            httpSession: HttpSession,
            request: LoginRequest
    ): ResponseEntity<CommonResponse> {
        val user = bookPiseoUserRepository.findByEmail(request.email)
        val encoder = BCryptPasswordEncoder()
        val hashPassword = encoder.encode(request.password)
        return if (encoder.matches(request.password, user.password)) {
            // 로그인 성공
            httpSession.setAttribute("user", user)
            ResponseEntity.ok(null)
        } else {
            // 로그인 실패
            ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(CommonResponse("INVALID_LOGIN_INFO", "아이디 또는 패스워드가 틀렸습니다."))
        }
    }
}