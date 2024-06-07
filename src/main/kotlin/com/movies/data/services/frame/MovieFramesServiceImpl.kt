package com.movies.data.services.frame

import com.movies.data.db.DatabaseFactory.dbQuery
import com.movies.data.db.Schema.MovieDataTable
import com.movies.data.db.Schema.MovieFramesTable
import com.movies.data.models.Frame
import com.movies.routes.arguments.FrameArgs
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.innerJoin
import org.jetbrains.exposed.sql.select


class MovieFramesServiceImpl: MovieFramesService {

    override suspend fun create(movieDataId: Long, urls: List<FrameArgs>): List<Frame>? {
        var statement : List<ResultRow>? = null

        dbQuery{
           statement =  MovieFramesTable.batchInsert(urls){ args->
                this[MovieFramesTable.movieDataId] = movieDataId
                this[MovieFramesTable.image_frame] = args.url
            }
        }

        return statement?.let { rowsToFrames(it) }
    }

    override suspend fun getAllFramesOfMovie(movieDataId: Long): List<Frame> {
        return dbQuery {
            MovieFramesTable.innerJoin(MovieDataTable,{ MovieFramesTable.movieDataId},{ MovieDataTable.id})
                .select{
                    MovieFramesTable.movieDataId eq movieDataId
            }.mapNotNull { rowToFrame(it) }
        }
    }

    private fun rowToFrame(row:ResultRow?): Frame?{
        return if(row == null) null
        else Frame(
            id = row[MovieFramesTable.id],
            url = row[MovieFramesTable.image_frame]
        )
    }

    private fun rowsToFrames(rows:List<ResultRow>?):List<Frame>?{
        if(rows==null) return null

        val listOfMovies = ArrayList<Frame>()
        rows.forEach{
            rowToFrame(it)?.let { it1 -> listOfMovies.add(it1) }
        }
        return listOfMovies
    }

}