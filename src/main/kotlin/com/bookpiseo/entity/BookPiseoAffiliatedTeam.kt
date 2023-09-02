package com.bookpiseo.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.sql.Timestamp
import java.time.ZonedDateTime
import java.util.*

@Entity
@Table(name = "book_piseo_affiliated_team", schema = "book-piseo", catalog = "")
class BookPiseoAffiliatedTeam(
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Id
        @Column(name = "ID")
        var id: Long = 0,

        @Basic
        @Column(name = "TEAM_ID")
        var teamId: String? = null,

        @Basic
        @Column(name = "USER_ID")
        var userId: String? = null,

        @Basic
        @Column(name = "IS_MASTER")
        var isMaster: Boolean,

        @CreationTimestamp
        @Column(name = "REG_DT")
        var regDt: ZonedDateTime? = null,
)
