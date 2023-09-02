package com.bookpiseo.exception

class BaseException(baseResponseEnumCode: BaseResponseCode) : RuntimeException() {
    public val baseResponseCode: BaseResponseCode = baseResponseEnumCode
}