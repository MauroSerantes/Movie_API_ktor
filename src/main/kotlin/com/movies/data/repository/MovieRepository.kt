package com.movies.data.repository

import com.movies.routes.arguments.*
import com.movies.base.BaseResponse

interface MovieRepository {
    suspend fun storeMovieData(moviesData:List<MovieArgs>) : BaseResponse
    suspend fun getMovieData(id:Long) : BaseResponse
    suspend fun getMovies(page:Int?,limit:Int?) : BaseResponse
    suspend fun deleteMovie(id:Long) : BaseResponse
    suspend fun updateTitlesOfMovie(movieId:Long,movieTitles:List<TitleArgs>) : BaseResponse
    suspend fun updateSynopsisOfMovie(movieId: Long,movieSynopsis:List<SynopsisArgs>) : BaseResponse
    suspend fun updateFramesOfMovie(movieId: Long,movieFrames:List<FrameArgs>): BaseResponse
    suspend fun updateVideosOfMovie(movieId: Long,movieVideos:List<VideoArgs>): BaseResponse
    suspend fun getFramesOfMovie(movieId: Long): BaseResponse
    suspend fun getVideosOfMovies(movieId: Long): BaseResponse
    suspend fun updateMovieTrailer(movieId: Long,trailerUrl:String): BaseResponse
}