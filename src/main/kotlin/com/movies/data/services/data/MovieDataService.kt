package com.movies.data.services.data


import com.movies.data.models.MovieData
import com.movies.data.models.commons.PaginatedResultsForMovieData
import com.movies.routes.arguments.MovieDataArgs


interface MovieDataService {
    suspend fun storeMovieData(moviesData:List<MovieDataArgs>):List<MovieData>?

    suspend fun getMovieById(id:Long): MovieData?

    suspend fun getMovies(page:Int?,limit:Int?): PaginatedResultsForMovieData

    suspend fun deleteMovieById(id: Long):Boolean

    suspend fun updateTrailerToMovie(id:Long,trailerUrl:String):Boolean
}