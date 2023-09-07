package com.bookpiseo.interceptor

import com.bookpiseo.exception.BaseException
import com.bookpiseo.exception.BaseResponseCode
import com.bookpiseo.service.UserService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class TokenInterceptor(
        private val userService: UserService
) : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        // OPTIONS 메서드 요청인 경우 Interceptor를 수행하지 않음
        if (request.method == "OPTIONS") {
            return true
        }
        userService.validateUserToken(request.getHeader("User-Token")
                ?: throw BaseException(BaseResponseCode.SESSION_EXPIRED))
        return true
    }

}