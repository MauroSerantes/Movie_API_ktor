package com.movies.data.services.frame

import com.movies.data.models.Frame
import com.movies.routes.arguments.FrameArgs

interface MovieFramesService {
    suspend fun create(movieDataId:Long,urls:List<FrameArgs>):List<Frame>?
    suspend fun getAllFramesOfMovie(movieDataId:Long):List<Frame>
}