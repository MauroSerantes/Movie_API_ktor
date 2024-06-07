package com.movies.routes.arguments

import kotlinx.serialization.Serializable

@Serializable
data class TitleArgs(
    val language: String,
    val content:String
)
