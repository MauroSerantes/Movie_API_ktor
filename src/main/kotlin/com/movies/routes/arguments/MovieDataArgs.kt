package com.movies.routes.arguments

import kotlinx.serialization.Serializable

@Serializable
data class MovieDataArgs(
    val duration: Int,
    val cover:String,
    val releaseYear:Int,
    val genres:String,
    val trailer:String?=null
)
