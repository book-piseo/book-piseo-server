package com.bookpiseo.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UuidGenerator
import java.sql.Timestamp
import java.time.ZonedDateTime
import java.util.*

@Entity
@Table(name = "book_piseo_contents", schema = "book-piseo", catalog = "")
class BookPiseoContents (
    @UuidGenerator
    @Id
    @Column(name = "CONTENTS_ID")
    var contentsId: String? = null,

    @Basic
    @Column(name = "CONTENTS_TITLE")
    var contentsTitle: String? = null,

    @Basic
    @Column(name = "CONTENTS_TEXT")
    var contentsText: Int? = null,

    @Basic
    @Column(name = "BOOK_INFO")
    var bookInfo: Any? = null,

    @Basic
    @Column(name = "TEAM_ID")
    var teamId: String? = null,

    @Basic
    @Column(name = "WRITE_USER_ID")
    var writeUserId: String? = null,

    @Basic
    @Column(name = "MOD_USER_ID")
    var modUserId: String? = null,

    @CreationTimestamp
    @Column(name = "REG_DT")
    var regDt: ZonedDateTime? = null,

    @Basic
    @Column(name = "MOD_DT")
    var modDt: ZonedDateTime? = null,
)
