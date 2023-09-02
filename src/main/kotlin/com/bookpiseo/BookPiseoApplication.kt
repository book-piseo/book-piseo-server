package com.bookpiseo

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.servers.Server
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@OpenAPIDefinition(
        servers = [
            Server(url = "https://book-piseo.com", description = "Default Server URL"),
            Server(url = "http://localhost:8080", description = "local Server URL")
        ]
)
@SpringBootApplication
class BookPiseoApplication

fun main(args: Array<String>) {
    runApplication<BookPiseoApplication>(*args)
}
