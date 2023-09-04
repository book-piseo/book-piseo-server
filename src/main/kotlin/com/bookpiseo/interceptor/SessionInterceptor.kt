package com.bookpiseo.interceptor

import com.bookpiseo.exception.BaseException
import com.bookpiseo.exception.BaseResponseCode
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class SessionInterceptor : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val userSession = request.getSession(false)
        if (userSession?.getAttribute("user") == null) {
            throw BaseException(BaseResponseCode.SESSION_EXPIRED)
        }
        return true
    }

}