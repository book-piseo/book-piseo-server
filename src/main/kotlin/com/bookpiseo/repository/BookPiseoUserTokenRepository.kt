package com.bookpiseo.repository

import com.bookpiseo.entity.BookPiseoUser
import com.bookpiseo.entity.BookPiseoUserToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookPiseoUserTokenRepository : JpaRepository<BookPiseoUserToken?, String?> {
    fun findByUserId(userId: String): BookPiseoUserToken?
    fun deleteByUserId(userId: String)
}