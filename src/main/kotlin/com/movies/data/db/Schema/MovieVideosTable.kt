package com.movies.data.db.Schema

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object MovieVideosTable: Table("movie_videos"){
    val id = long("id").autoIncrement()
    val movieDataId = long("movie_video_data").references(MovieDataTable.id,onDelete = ReferenceOption.CASCADE)
    val server = varchar("server",1000)
    val movieUrl = varchar("movie_url",3000)

    override val primaryKey = PrimaryKey(id)
}