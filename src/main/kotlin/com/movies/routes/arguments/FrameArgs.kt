package com.movies.routes.arguments

import kotlinx.serialization.Serializable

@Serializable
data class FrameArgs(
    val url:String
)