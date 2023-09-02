package com.bookpiseo.exception

import org.springframework.http.HttpStatus

enum class BaseResponseCode(status: HttpStatus, code: String, message: String) {
    INVALID_LOGIN_INFO(HttpStatus.UNAUTHORIZED, "INVALID_LOGIN_INFO", "아이디 또는 패스워드가 틀렸습니다."),
    SESSION_EXPIRED(HttpStatus.FORBIDDEN, "SESSION_EXPIRED", "세션이 만료되었습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "알 수 없는 서버 에러가 발생하였습니다.");

    val httpStatus: HttpStatus = status
    val errorCode: String = code
    val errorMessage: String = message
}