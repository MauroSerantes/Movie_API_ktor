package com.movies.data.models

import kotlinx.serialization.Serializable


@Serializable
data class MovieData(
    val id:Long,
    val duration: Int,
    val cover:String,
    val releaseYear:Int,
    val genres:String,
    val trailer:String?=null
)

