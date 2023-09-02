package com.bookpiseo.repository

import com.bookpiseo.entity.BookPiseoTeam
import com.bookpiseo.entity.BookPiseoUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookPiseoTeamRepository : JpaRepository<BookPiseoTeam?, String?> {
}