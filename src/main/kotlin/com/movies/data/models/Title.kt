package com.movies.data.models

import com.movies.base.SerializableDTO
import kotlinx.serialization.Serializable

@Serializable
data class Title(
    val id:Long,
    val language:String,
    val content:String
): SerializableDTO

@Serializable
data class ListOfTitles(
    val listOfTitles:List<Title>
): SerializableDTO