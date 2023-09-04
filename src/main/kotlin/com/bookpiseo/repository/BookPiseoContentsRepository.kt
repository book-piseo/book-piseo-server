package com.bookpiseo.repository

import com.bookpiseo.entity.BookPiseoContents
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookPiseoContentsRepository : JpaRepository<BookPiseoContents?, String?> {
    fun findAllByTeamIdIn(teamIds: List<String>, pageable: Pageable): Page<BookPiseoContents?>?
    fun findAllByTeamId(teamId: String, pageable: Pageable): Page<BookPiseoContents?>?

}