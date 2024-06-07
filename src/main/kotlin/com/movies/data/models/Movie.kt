package com.movies.data.models

import com.movies.base.SerializableDTO
import kotlinx.serialization.Serializable


@Serializable
data class Movie(
    val id:Long,
    val title:List<Title>,
    val duration: Int,
    val synopsis:List<Synopsis>,
    val cover:String,
    val releaseYear:Int,
    val movieFrames:List<Frame>?=null,
    val genres:String,
    val trailer:String?=null,
    val moviesVideos:List<Video>?=null
): SerializableDTO {

    constructor(
        movieData: MovieData,
        movieTitles:List<Title>,
        movieSynopsis:List<Synopsis>,
        movieFrames:List<Frame>,
        movieVideos: List<Video>
    ):this(
        id = movieData.id,
        title = movieTitles,
        duration = movieData.duration,
        synopsis = movieSynopsis,
        cover = movieData.cover,
        releaseYear = movieData.releaseYear,
        movieFrames = movieFrames,
        genres = movieData.genres
    )
}


@Serializable
data class ListOfMovies(
    val listOfMovies:List<Movie>
): SerializableDTO