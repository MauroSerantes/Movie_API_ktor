package com.movies

import com.movies.config.configureContentNegotiation
import com.movies.config.configureDatabase
import com.movies.config.configureRouting
import io.ktor.server.application.*


fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureDatabase()
    configureContentNegotiation()
    configureRouting()
}
