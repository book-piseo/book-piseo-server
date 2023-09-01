package com.bookpiseo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BookPiseoApplication

fun main(args: Array<String>) {
    runApplication<BookPiseoApplication>(*args)
}
