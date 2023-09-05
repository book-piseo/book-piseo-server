package com.bookpiseo.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UuidGenerator
import java.time.ZonedDateTime

@Entity
@Table(name = "book_piseo_user_token", schema = "book-piseo", catalog = "")
class BookPiseoUserToken(
        @UuidGenerator
        @Id
        @Column(name = "TOKEN_ID")
        var tokenId: String? = null,

        @Column(name = "USER_ID")
        var userId: String,
)
