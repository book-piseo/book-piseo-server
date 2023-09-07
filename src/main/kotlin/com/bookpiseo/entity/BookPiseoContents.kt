package com.bookpiseo.entity

import com.bookpiseo.dto.BookInfo
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UuidGenerator
import java.time.ZonedDateTime

@Entity
@Table(name = "book_piseo_contents", schema = "book-piseo", catalog = "")
class BookPiseoContents(
        @UuidGenerator
        @Id
        @Column(name = "CONTENTS_ID")
        var contentsId: String? = null,

        @Basic
        @Column(name = "CONTENTS_TITLE")
        var contentsTitle: String,

        @Basic
        @Column(name = "CONTENTS_TEXT")
        var contentsText: String? = null,


        @Column(name = "BOOK_INFO")
        @Convert(converter = BookInfo.BookInfoConverter::class)
        var bookInfo: BookInfo,

        @Basic
        @Column(name = "TEAM_ID")
        var teamId: String,

        @Basic
        @Column(name = "WRITER_ID")
        var writerId: String? = null,

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
