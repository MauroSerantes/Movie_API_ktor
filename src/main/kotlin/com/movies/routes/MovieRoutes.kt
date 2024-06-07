package com.movies.routes

import com.movies.data.repository.MovieRepository
import com.movies.routes.arguments.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.movieRoutes(
    movieRepository: MovieRepository
){
    routing {
        route("/movie"){
            post("/store"){
                val movieData = call.receive<List<MovieArgs>>()
                val result = movieRepository.storeMovieData(movieData)
                call.respond(result.statusCode,result)
            }
            put("{id}/update/trailer"){
                val id = call.parameters["id"]?.toLongOrNull()?:-1
                val movieTrailer = call.receive<String>()
                val result = movieRepository.updateMovieTrailer(id,movieTrailer)
                call.respond(result.statusCode,result)
            }
            post("{id}/videos/store"){
                val id = call.parameters["id"]?.toLongOrNull()?:-1
                val movieVideos = call.receive<List<VideoArgs>>()
                val result = movieRepository.updateVideosOfMovie(id,movieVideos)
                call.respond(result.statusCode,result)
            }
            post("{id}/frames/store"){
                val id = call.parameters["id"]?.toLongOrNull()?:-1
                val movieFrames = call.receive<List<FrameArgs>>()
                val result = movieRepository.updateFramesOfMovie(id,movieFrames)
                call.respond(result.statusCode,result)
            }
            post("{id}/synopsis/store"){
                val id = call.parameters["id"]?.toLongOrNull()?:-1
                val movieSynopsis = call.receive<List<SynopsisArgs>>()
                val result = movieRepository.updateSynopsisOfMovie(id,movieSynopsis)
                call.respond(result.statusCode,result)
            }
            post("{id}/titles/store"){
                val id = call.parameters["id"]?.toLongOrNull()?:-1
                val movieTitles = call.receive<List<TitleArgs>>()
                val result = movieRepository.updateTitlesOfMovie(id,movieTitles)
                call.respond(result.statusCode,result)
            }
            get("/all/{page}"){
                val page = call.parameters["page"]?.toIntOrNull()?:0
                val limit = call.request.queryParameters["limit"]?.toIntOrNull()?:20
                val result = movieRepository.getMovies(page,limit)
                call.respond(result.statusCode,result)
            }
            get("/{id}"){
                val id = call.parameters["id"]?.toLongOrNull()?:-1
                val result = movieRepository.getMovieData(id)
                call.respond(result.statusCode,result)
            }
            get("/{id}/videos"){
                val id = call.parameters["id"]?.toLongOrNull()?:-1
                val result = movieRepository.getVideosOfMovies(id)
                call.respond(result.statusCode,result)
            }
            get("/{id}/frames"){
                val id = call.parameters["id"]?.toLongOrNull()?:-1
                val result = movieRepository.getFramesOfMovie(id)
            }
            delete("{id}/delete") {
                val id = call.parameters["id"]?.toLongOrNull()?:-1
                val result = movieRepository.deleteMovie(id)
                call.respond(result.statusCode,result)
            }
        }
    }
}