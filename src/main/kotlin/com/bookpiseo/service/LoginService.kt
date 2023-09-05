package com.bookpiseo.service


import com.bookpiseo.dto.Login
import com.bookpiseo.dto.UserInfo
import com.bookpiseo.entity.BookPiseoUserToken
import com.bookpiseo.exception.BaseException
import com.bookpiseo.exception.BaseResponseCode
import com.bookpiseo.repository.BookPiseoAffiliatedTeamRepository
import com.bookpiseo.repository.BookPiseoUserRepository
import com.bookpiseo.repository.BookPiseoUserTokenRepository
import jakarta.servlet.http.HttpSession
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LoginService(
        val bookPiseoUserRepository: BookPiseoUserRepository,
        val bookPiseoUserTokenRepository: BookPiseoUserTokenRepository
) {

    @Transactional(rollbackFor = [Exception::class])
    fun login(
            request: Login.LoginRequest
    ): Login.LoginResponse {
        val user = bookPiseoUserRepository.findByEmail(request.email)
                ?: throw BaseException(BaseResponseCode.INVALID_LOGIN_INFO)
        val encoder = BCryptPasswordEncoder()
        if (encoder.matches(request.password, user.password)) {
            // 로그인 성공
            bookPiseoUserTokenRepository.deleteByUserId(user.userId!!)

            return Login.LoginResponse(tokenId = bookPiseoUserTokenRepository.saveAndFlush(BookPiseoUserToken(userId = user.userId!!)).tokenId!!)
        }

        // 로그인 실패
        throw BaseException(BaseResponseCode.INVALID_LOGIN_INFO)
    }
}