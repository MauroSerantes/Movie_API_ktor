package com.movies.data.db.Schema


import org.jetbrains.exposed.sql.Table

object MovieDataTable:Table("movie_data"){
    val id = long("id").autoIncrement()
    val duration = integer("duration")
    val cover = varchar("cover",3000)
    val releaseYear = integer("release_year")
    val genres = varchar("genres",1000)
    val trailer = varchar("movie_trailer",3000).nullable()

    override val primaryKey = PrimaryKey(id)
}

//varchar("title",255)
