package com.movies.routes.arguments

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieArgs(
    val title:List<TitleArgs>,
    val duration: Int,
    val synopsis:List<SynopsisArgs>,
    val cover:String,
    @SerialName("release")
    val releaseYear:Int,
    @SerialName("frames")
    val movieFrames:List<FrameArgs>?=null,
    val genres:String,
    val trailer:String ?= null,
    val videos:List<VideoArgs>?=null
)