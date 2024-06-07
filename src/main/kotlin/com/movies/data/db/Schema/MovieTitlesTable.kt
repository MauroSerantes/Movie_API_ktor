package com.movies.data.db.Schema

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object MovieTitlesTable:Table("movies_titles"){
    val id = long("id").autoIncrement()
    val movieDataId = long("movie_data_id").references(MovieDataTable.id,onDelete = ReferenceOption.CASCADE)
    val language = varchar("language",200)
    val title = varchar("title",1500)

    override val primaryKey = PrimaryKey(id)
}