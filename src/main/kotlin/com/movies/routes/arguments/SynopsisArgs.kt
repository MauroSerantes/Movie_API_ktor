package com.movies.routes.arguments

import kotlinx.serialization.Serializable

@Serializable
data class SynopsisArgs(
    val language: String,
    val content:String
)
