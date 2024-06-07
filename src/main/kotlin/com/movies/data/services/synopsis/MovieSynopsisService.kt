package com.movies.data.services.synopsis

import com.movies.data.models.Synopsis
import com.movies.routes.arguments.SynopsisArgs

interface MovieSynopsisService {
    suspend fun create(movieDataId:Long,synopsisContents:List<SynopsisArgs>): List<Synopsis>?
    suspend fun getAllSynopsisOfMovie(movieDataId:Long):List<Synopsis>
}