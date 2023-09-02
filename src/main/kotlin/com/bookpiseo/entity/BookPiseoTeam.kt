package com.bookpiseo.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UuidGenerator
import java.sql.Timestamp
import java.time.ZonedDateTime
import java.util.*

@Entity
@Table(name = "book_piseo_team", schema = "book-piseo", catalog = "")
class BookPiseoTeam (
    @UuidGenerator
    @Id
    @Column(name = "TEAM_ID")
    var teamId: String? = null,

    @Basic
    @Column(name = "TEAM_NAME")
    var teamName: String? = null,

    @Basic
    @Column(name = "TEAM_DESCRIPTION")
    var teamDescription: String? = null,

    @Basic
    @Column(name = "TEAM_IMG")
    var teamImg: String? = null,

    @CreationTimestamp
    @Column(name = "REG_DT")
    var regDt: ZonedDateTime? = null,

    @Basic
    @Column(name = "MOD_DT")
    var modDt: ZonedDateTime? = null,

    @Basic
    @Column(name = "REG_USER_ID")
    var regUserId: String? = null,
)
