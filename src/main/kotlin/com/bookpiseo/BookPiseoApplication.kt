package com.bookpiseo

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
@OpenAPIDefinition
@SpringBootApplication
class BookPiseoApplication

fun main(args: Array<String>) {
    runApplication<BookPiseoApplication>(*args)
}
