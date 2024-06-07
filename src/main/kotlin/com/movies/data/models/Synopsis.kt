package com.movies.data.models

import com.movies.base.SerializableDTO
import kotlinx.serialization.Serializable

@Serializable
data class Synopsis(
    val id:Long,
    val language: String,
    val content: String
): SerializableDTO

@Serializable
data class ListOfSynopsis(
    val listOfSynopsis:List<Synopsis>
): SerializableDTO