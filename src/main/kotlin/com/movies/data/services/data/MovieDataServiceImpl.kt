package com.movies.data.services.data

import com.movies.data.db.DatabaseFactory.dbQuery
import com.movies.data.db.Schema.MovieDataTable
import com.movies.data.models.MovieData
import com.movies.data.models.commons.PaginatedResultsForMovieData
import com.movies.routes.arguments.MovieDataArgs
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class MovieDataServiceImpl: MovieDataService {

    override suspend fun storeMovieData(moviesData: List<MovieDataArgs>): List<MovieData>? {
        var statement:List<ResultRow>?=null

        dbQuery {
            statement = MovieDataTable.batchInsert(moviesData){ movieData ->
                this[MovieDataTable.cover] = movieData.cover
                this[MovieDataTable.duration] = movieData.duration
                this[MovieDataTable.releaseYear] = movieData.releaseYear
                this[MovieDataTable.genres] = movieData.genres
                this[MovieDataTable.cover] = movieData.cover
                this[MovieDataTable.trailer] = movieData.trailer
            }
        }

        return statement?.let { rowsToMovies(it) }
    }

    override suspend fun getMovieById(id: Long): MovieData? = dbQuery {
        MovieDataTable.select{ MovieDataTable.id.eq(id)}
            .map { rowToMovie(it) }.singleOrNull()
    }


    override suspend fun getMovies(page:Int?,limit:Int?): PaginatedResultsForMovieData {
        var pageCount:Int = 0
        var nextPage:Int? = null
        var prevPage:Int? = null


        val movies = dbQuery {
            MovieDataTable.select { MovieDataTable.id greater(0)}.also {
                pageCount = (it.count()/(limit!!)).toInt()
                if(page!! < pageCount){
                    nextPage = page + 1
                }
                if( page > pageCount){
                    prevPage = page - 1
                }
            }.limit(limit!!,(limit * page!!).toLong())
                .mapNotNull { rowToMovie(it) }
        }

        return PaginatedResultsForMovieData(pageCount, prevPage = prevPage, nextPage = nextPage, results = movies)
    }


    override suspend fun deleteMovieById(id: Long):Boolean {
        var result = -1
        dbQuery {
           result =  MovieDataTable.deleteWhere { MovieDataTable.id.eq(id) }
        }
        return result == 1
    }

    override suspend fun updateTrailerToMovie(id: Long, trailerUrl: String): Boolean {
        var result = -1
        dbQuery {
            result = MovieDataTable.update({ MovieDataTable.id eq(id)}){
                it[MovieDataTable.trailer] = trailerUrl
            }
        }
        return result == 1
    }


    private fun rowToMovie(row:ResultRow?): MovieData?{
        return if(row == null) null
        else MovieData(
            id =  row[MovieDataTable.id],
            duration = row[MovieDataTable.duration],
            cover = row[MovieDataTable.cover],
            releaseYear = row[MovieDataTable.releaseYear],
            genres = row[MovieDataTable.genres],
            trailer = row[MovieDataTable.trailer]
        )
    }


   private fun rowsToMovies(rows:List<ResultRow>?):List<MovieData>?{
       if(rows==null) return null

       val listOfMovies = ArrayList<MovieData>()
       rows.forEach{
           rowToMovie(it)?.let { it1 -> listOfMovies.add(it1) }
       }
       return listOfMovies
   }
}