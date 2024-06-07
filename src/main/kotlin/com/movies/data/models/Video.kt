package com.movies.data.models

import com.movies.base.SerializableDTO
import kotlinx.serialization.Serializable


@Serializable
data class Video(
    val id:Long,
    val server:String,
    val url:String,
): SerializableDTO

@Serializable
data class ListOfVideos(
    val listOfVideos:List<Video>
): SerializableDTO
