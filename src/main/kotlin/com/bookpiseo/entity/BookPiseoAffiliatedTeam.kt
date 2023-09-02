package com.bookpiseo.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.ZonedDateTime

@Entity
@Table(name = "book_piseo_affiliated_team", schema = "book-piseo", catalog = "")
class BookPiseoAffiliatedTeam(
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Id
        @Column(name = "ID")
        var id: Long = 0,

        @ManyToOne
        @JoinColumn(name = "TEAM_ID", referencedColumnName = "TEAM_ID")
        var bookPiseoTeam: BookPiseoTeam,

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
