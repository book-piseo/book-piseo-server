package com.bookpiseo.service


import com.bookpiseo.dto.UserInfo
import com.bookpiseo.exception.BaseException
import com.bookpiseo.exception.BaseResponseCode
import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Service

@Service
class UserService() {
    fun getUserSessionInfo(httpSession: HttpSession): UserInfo.UserSessionInfo {
        val userSessionInfo = httpSession.getAttribute("user")?.let { user ->
            user as UserInfo.UserSessionInfo
        }
        return userSessionInfo ?: throw BaseException(BaseResponseCode.SESSION_EXPIRED)
    }

}