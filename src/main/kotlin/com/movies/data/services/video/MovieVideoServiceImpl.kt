package com.movies.data.services.video

import com.movies.data.db.DatabaseFactory.dbQuery
import com.movies.data.db.Schema.MovieDataTable
import com.movies.data.db.Schema.MovieVideosTable
import com.movies.data.models.Video
import com.movies.routes.arguments.VideoArgs
import org.jetbrains.exposed.sql.*

class MovieVideoServiceImpl: MovieVideoService {
    override suspend fun create(movieId: Long, listOfVideos: List<VideoArgs>): List<Video>? {
        var statement:List<ResultRow>?=null

        dbQuery {
            statement = MovieVideosTable.batchInsert(listOfVideos){ video ->
                this[MovieVideosTable.movieDataId] = movieId
                this[MovieVideosTable.server] = video.server
                this[MovieVideosTable.movieUrl] = video.url
            }
        }

        return statement?.let { rowsToVideos(it) }
    }

    override suspend fun getAllMovieVideos(movieId: Long): List<Video> {
        return dbQuery {
            MovieVideosTable
                .innerJoin(MovieDataTable,{ MovieVideosTable.movieDataId},{ MovieDataTable.id})
                .select { MovieVideosTable.movieDataId eq movieId}
                .mapNotNull { rowToVideo(it) }
        }
    }

    private fun rowToVideo(row:ResultRow?): Video?{
        return if(row == null) null
        else Video(
            id = row[MovieVideosTable.id],
            server = row[MovieVideosTable.server],
            url = row[MovieVideosTable.movieUrl]
        )
    }

    private fun rowsToVideos(rows:List<ResultRow>?):List<Video>?{
        if(rows==null) return null

        val listOfMovies = ArrayList<Video>()
        rows.forEach{
            rowToVideo(it)?.let { it1 -> listOfMovies.add(it1) }
        }
        return listOfMovies
    }

}