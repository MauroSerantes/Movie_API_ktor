package com.movies.data.db.Schema

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object MovieSynopsisTable:Table("movies_synopsis") {
    val id = long("id").autoIncrement()
    val movieDataId = long("movie_data_id").references(MovieDataTable.id,onDelete = ReferenceOption.CASCADE)
    val language = varchar("language",100)
    val synopsis = text("synopsis")

    override val primaryKey = PrimaryKey(id)
}