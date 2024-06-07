package com.movies.data.services.title

import com.movies.data.db.DatabaseFactory.dbQuery
import com.movies.data.db.Schema.MovieDataTable
import com.movies.data.db.Schema.MovieTitlesTable
import com.movies.data.models.Title
import com.movies.routes.arguments.TitleArgs
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.innerJoin
import org.jetbrains.exposed.sql.select

class MovieTitleServiceImpl: MovieTitleService {

    override suspend fun create(movieDataId: Long, titles: List<TitleArgs>): List<Title>? {
       var statement:List<ResultRow>?=null

        dbQuery{
            statement = MovieTitlesTable.batchInsert(titles){ titleData->
                this[MovieTitlesTable.movieDataId] = movieDataId
                this[MovieTitlesTable.language] = titleData.language
                this[MovieTitlesTable.title] = titleData.content
            }
        }

        return statement?.let { rowsToTitles(it) }
    }

    override suspend fun getAllTitlesOfMovie(movieDataId: Long): List<Title> {
        return dbQuery {
            MovieTitlesTable
                .innerJoin(MovieDataTable,{ MovieTitlesTable.movieDataId},{ MovieDataTable.id})
                .select{ MovieTitlesTable.movieDataId eq movieDataId}
                .mapNotNull { rowToTitle(it) }
        }
    }

    private fun rowToTitle(row:ResultRow?): Title?{
        return if(row == null) null
        else Title(
            id = row[MovieTitlesTable.id],
            language = row[MovieTitlesTable.language],
            content = row[MovieTitlesTable.title]
        )
    }

    private fun rowsToTitles(rows:List<ResultRow>?):List<Title>?{
        if(rows==null) return null

        val listOfMovies = ArrayList<Title>()
        rows.forEach{
            rowToTitle(it)?.let { it1 -> listOfMovies.add(it1) }
        }
        return listOfMovies
    }
}