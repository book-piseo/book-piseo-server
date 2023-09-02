package com.bookpiseo.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UuidGenerator
import java.time.ZonedDateTime

@Entity
@Table(name = "book_piseo_user", schema = "book-piseo", catalog = "")
class BookPiseoUser(
        @UuidGenerator
        @Id
        @Column(name = "USER_ID")
        var userId: String? = null,

        @Basic
        @Column(name = "EMAIL")
        var email: String,

        @Basic
        @Column(name = "PASSWORD")
        var password: String,

        @Basic
        @Column(name = "USER_NAME")
        var userName: String,

        @Basic
        @Column(name = "PHONE")
        var phone: String? = null,

        @Basic
        @Column(name = "PROFILE_IMG")
        var profileImg: String? = null,

        @CreationTimestamp
        @Column(name = "REG_DT")
        var regDt: ZonedDateTime? = null,

        @Basic
        @Column(name = "MOD_DT")
        var modDt: ZonedDateTime? = null,
)
