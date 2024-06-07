package com.movies.data.services.title

import com.movies.data.models.Title
import com.movies.routes.arguments.TitleArgs

interface MovieTitleService {
    suspend fun create(movieDataId:Long,titles:List<TitleArgs>): List<Title>?
    suspend fun getAllTitlesOfMovie(movieDataId:Long):List<Title>
}