package com.bookpiseo.repository

import com.bookpiseo.entity.BookPiseoContents
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookPiseoContentsRepository : JpaRepository<BookPiseoContents?, Long?> {
    fun findAllByTeamIdIn(teamIds: List<String>): List<BookPiseoContents>
    fun findAllByTeamId(teamId: String): List<BookPiseoContents>
}