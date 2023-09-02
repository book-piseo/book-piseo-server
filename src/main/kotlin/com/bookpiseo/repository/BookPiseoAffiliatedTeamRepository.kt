package com.bookpiseo.repository

import com.bookpiseo.entity.BookPiseoAffiliatedTeam
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookPiseoAffiliatedTeamRepository : JpaRepository<BookPiseoAffiliatedTeam?, Long?> {
    fun findAllByUserId(userId: String): List<BookPiseoAffiliatedTeam>
}