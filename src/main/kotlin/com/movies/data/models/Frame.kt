package com.movies.data.models

import com.movies.base.SerializableDTO
import kotlinx.serialization.Serializable


@Serializable
data class Frame(
    val id:Long,
    val url:String
): SerializableDTO

@Serializable
data class ListOfFrames(
    val listOfTitles:List<Frame>
): SerializableDTO