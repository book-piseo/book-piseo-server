package com.bookpiseo.exception

class BaseException(baseResponseEnumCode: BaseResponseCode) : RuntimeException() {
    val baseResponseCode: BaseResponseCode = baseResponseEnumCode
}