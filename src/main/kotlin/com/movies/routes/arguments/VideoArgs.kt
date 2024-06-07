package com.movies.routes.arguments

import kotlinx.serialization.Serializable

@Serializable
data class VideoArgs(
    val server:String,
    val url:String
)
