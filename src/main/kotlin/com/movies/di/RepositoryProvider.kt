package com.movies.di

import com.movies.data.repository.MovieRepositoryImpl
import com.movies.data.services.data.MovieDataServiceImpl
import com.movies.data.services.frame.MovieFramesServiceImpl
import com.movies.data.services.synopsis.MovieSynopsisServiceImpl
import com.movies.data.services.title.MovieTitleServiceImpl
import com.movies.data.services.video.MovieVideoServiceImpl

object RepositoryProvider {
    fun provideMovieRepository():MovieRepositoryImpl = MovieRepositoryImpl(
        MovieDataServiceImpl(),
        MovieTitleServiceImpl(),
        MovieSynopsisServiceImpl(),
        MovieFramesServiceImpl(),
        MovieVideoServiceImpl()
    )
}