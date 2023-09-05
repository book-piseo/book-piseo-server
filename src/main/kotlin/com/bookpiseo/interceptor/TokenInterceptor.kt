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
        userService.validateUserToken(request.getHeader("User-Token")
                ?: throw BaseException(BaseResponseCode.SESSION_EXPIRED))
        return true
    }

}