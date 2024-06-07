package com.movies.data.services.synopsis

import com.movies.data.db.DatabaseFactory.dbQuery
import com.movies.data.db.Schema.MovieDataTable
import com.movies.data.db.Schema.MovieSynopsisTable
import com.movies.data.models.Synopsis
import com.movies.routes.arguments.SynopsisArgs
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.innerJoin
import org.jetbrains.exposed.sql.select

class MovieSynopsisServiceImpl: MovieSynopsisService {

    override suspend fun create(movieDataId: Long, synopsisContents: List<SynopsisArgs>): List<Synopsis>? {
        var statement:List<ResultRow>?=null

        dbQuery{
            statement = MovieSynopsisTable.batchInsert(synopsisContents){ synopsisData ->
                this[MovieSynopsisTable.movieDataId] = movieDataId
                this[MovieSynopsisTable.language] = synopsisData.language
                this[MovieSynopsisTable.synopsis] = synopsisData.content
            }
        }

        return statement?.let { rowsToSynopsis(it) }
    }

    override suspend fun getAllSynopsisOfMovie(movieDataId: Long): List<Synopsis> {
        return dbQuery {
            MovieSynopsisTable
                .innerJoin(MovieDataTable,{ MovieSynopsisTable.movieDataId},{ MovieDataTable.id})
                .select{ MovieSynopsisTable.movieDataId eq  movieDataId}
                .mapNotNull { rowToSynopsis(it) }
        }
    }


    private fun rowToSynopsis(row:ResultRow?): Synopsis?{
        return if(row == null) null
        else Synopsis(
            id = row[MovieSynopsisTable.id],
            language = row[MovieSynopsisTable.language],
            content = row[MovieSynopsisTable.synopsis]
        )
    }

    private fun rowsToSynopsis(rows:List<ResultRow>?):List<Synopsis>?{
        if(rows==null) return null

        val listOfMovies = ArrayList<Synopsis>()
        rows.forEach{
            rowToSynopsis(it)?.let { it1 -> listOfMovies.add(it1) }
        }
        return listOfMovies
    }
}