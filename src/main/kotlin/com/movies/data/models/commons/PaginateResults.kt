package com.movies.data.models.commons

import com.movies.base.SerializableDTO
import com.movies.data.models.*
import kotlinx.serialization.Serializable


@Serializable
data class PaginateResults(
    val pageCount:Int,
    val prevPage:Int?,
    val nextPage:Int?,
    val results:List<Movie>
): SerializableDTO {
    constructor(
        paginatedResultsForMovieData: PaginatedResultsForMovieData,
        title: List<List<Title>>,
        synopsis:List<List<Synopsis>>,
        frame:List<List<Frame>>,
        video:List<List<Video>>
    ):this(
        paginatedResultsForMovieData.pageCount,
        paginatedResultsForMovieData.prevPage,
        paginatedResultsForMovieData.nextPage,
        ArrayList<Movie>().apply {
            for(i in paginatedResultsForMovieData.results.indices){
                add(Movie(paginatedResultsForMovieData.results[i],title[i], synopsis[i], frame[i],video[i]))
            }
        }
    )
}


data class PaginatedResultsForMovieData(
    val pageCount:Int,
    val prevPage:Int?,
    val nextPage:Int?,
    val results:List<MovieData>
)