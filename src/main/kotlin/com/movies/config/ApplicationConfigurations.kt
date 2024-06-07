package com.movies.config

import com.movies.base.SerializableDTO
import com.movies.data.db.DatabaseFactory
import com.movies.data.models.ListOfMovies
import com.movies.data.models.Movie
import com.movies.config.serializers.HttpStatusCodeSerializer
import com.movies.di.RepositoryProvider
import com.movies.routes.movieRoutes
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

fun Application.configureDatabase(){
    DatabaseFactory.init()
}

fun Application.configureContentNegotiation(){
    install(ContentNegotiation){
        json(
            Json {
                prettyPrint = true
                isLenient = true
                serializersModule = SerializersModule {
                    contextual(HttpStatusCode::class) { HttpStatusCodeSerializer }
                    polymorphic(SerializableDTO::class){
                        subclass(Movie::class)
                        subclass(ListOfMovies::class)
                    }
                }
            }
        )
    }
}

fun Application.configureRouting(){
    movieRoutes(RepositoryProvider.provideMovieRepository())
}