package com.movies.data.db.Schema

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object MovieFramesTable:Table("movies_frames") {
    val id = long("id").autoIncrement()
    val movieDataId = long("movie_data_id").references(MovieDataTable.id,onDelete =  ReferenceOption.CASCADE)
    val image_frame = varchar("image_url",3000)

    override val primaryKey = PrimaryKey(id)
}