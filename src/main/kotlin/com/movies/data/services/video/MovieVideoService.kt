package com.movies.data.services.video

import com.movies.data.models.Video
import com.movies.routes.arguments.VideoArgs

interface MovieVideoService {
    suspend fun create(movieId:Long,listOfVideos:List<VideoArgs>):List<Video>?
    suspend fun getAllMovieVideos(movieId: Long):List<Video>
}