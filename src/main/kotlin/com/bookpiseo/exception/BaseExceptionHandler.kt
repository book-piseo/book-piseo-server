package com.bookpiseo.exception

import com.bookpiseo.dto.ErrorResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class BaseExceptionHandler {
    @ExceptionHandler(Exception::class)
    protected fun handleBaseException(e: Exception): ResponseEntity<ErrorResponse> {
        if (e is BaseException) {
            return ResponseEntity.status(e.baseResponseCode.httpStatus)
                    .body(ErrorResponse(
                            errorCode = e.baseResponseCode.errorCode,
                            errorMessage = e.baseResponseCode.errorMessage))
        } else {
            val internalServerError = BaseResponseCode.INTERNAL_SERVER_ERROR
            return ResponseEntity.status(internalServerError.httpStatus)
                    .body(ErrorResponse(
                            errorCode = internalServerError.errorCode,
                            errorMessage = internalServerError.errorMessage
                    ))
        }

    }
}