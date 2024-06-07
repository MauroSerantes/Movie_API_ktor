package com.movies.data.repository

import com.movies.data.models.commons.PaginateResults
import com.movies.routes.arguments.*
import com.movies.base.BaseResponse
import com.movies.config.RespondMessages
import com.movies.data.models.*
import com.movies.data.services.data.MovieDataService
import com.movies.data.services.frame.MovieFramesService
import com.movies.data.services.synopsis.MovieSynopsisService
import com.movies.data.services.title.MovieTitleService
import com.movies.data.services.video.MovieVideoService


class MovieRepositoryImpl(
    private val movieDataService: MovieDataService,
    private val movieTitleService: MovieTitleService,
    private val movieSynopsisService: MovieSynopsisService,
    private val movieFramesService: MovieFramesService,
    private val movieVideosService: MovieVideoService
): MovieRepository {

    override suspend fun storeMovieData(moviesData: List<MovieArgs>): BaseResponse {

        val listOfMovieData = movieDataService.storeMovieData(moviesData.map { movie ->
            MovieDataArgs(movie.duration,movie.cover,movie.releaseYear,movie.genres)
        })

        val titles = ArrayList<List<Title>?>()
        val synopsis = ArrayList<List<Synopsis>?>()
        val frames = ArrayList<List<Frame>?>()
        val videos = ArrayList<List<Video>?>()

        if(listOfMovieData!=null){
            for(i in listOfMovieData.indices){
                titles.add(movieTitleService.create(listOfMovieData[i].id, moviesData[i].title))
                synopsis.add(movieSynopsisService.create(listOfMovieData[i].id, moviesData[i].synopsis))
                frames.add(movieFramesService.create(listOfMovieData[i].id,moviesData[i].movieFrames?: emptyList()))
                videos.add(movieVideosService.create(listOfMovieData[i].id,moviesData[i].videos?: emptyList()))
            }
        }

        val listOfMovies = if(listOfMovieData!=null){
            ArrayList<Movie>()
        }else null

        listOfMovies?.let {
            for(i in listOfMovieData?.indices!!){

                titles[i]?.let { it1 -> synopsis[i]?.let { it2 ->
                    frames[i]?.let { it3 ->
                        videos[i]?.let { it4 ->
                            Movie(movieData = listOfMovieData[i], it1,
                                it2, it3, it4
                            )
                        }
                    }
                } }
                    ?.let { it2 ->
                        listOfMovies.add(
                            it2
                        )
                    }
            }
        }

        return if(listOfMovies!=null){
            BaseResponse.SuccessResponse(ListOfMovies(listOfMovies),RespondMessages.SUCCESS)
        }
        else{
            BaseResponse.ErrorResponse(message = RespondMessages.GENERAL_ERROR)
        }
    }

    override suspend fun getMovieData(id: Long): BaseResponse {
        val movieData = movieDataService.getMovieById(id)

        val titles = movieTitleService.getAllTitlesOfMovie(id)
        val synopsis = movieSynopsisService.getAllSynopsisOfMovie(id)
        val frames = movieFramesService.getAllFramesOfMovie(id)
        val videos = movieVideosService.getAllMovieVideos(id)

        return if(movieData!=null){
            BaseResponse.SuccessResponse(Movie(movieData,titles,synopsis,frames,videos),RespondMessages.SUCCESS)
        }else BaseResponse.ErrorResponse(message = RespondMessages.GENERAL_ERROR)
    }

    override suspend fun getMovies(page: Int?, limit: Int?): BaseResponse {
        val moviesData = movieDataService.getMovies(page,limit)

        val titles = ArrayList<List<Title>>()
        val synopsis = ArrayList<List<Synopsis>>()
        val frames = ArrayList<List<Frame>>()
        val videos = ArrayList<List<Video>>()


        for(i in moviesData.results.indices){
            titles.add(movieTitleService.getAllTitlesOfMovie(moviesData.results[i].id))
            synopsis.add(movieSynopsisService.getAllSynopsisOfMovie(moviesData.results[i].id))
            frames.add(movieFramesService.getAllFramesOfMovie(moviesData.results[i].id))
            videos.add(movieVideosService.getAllMovieVideos(moviesData.results[i].id))
        }

        return BaseResponse.SuccessResponse(PaginateResults(moviesData,titles,synopsis,frames,videos),RespondMessages.SUCCESS)
    }

    override suspend fun deleteMovie(id: Long): BaseResponse {
       val deleted = movieDataService.deleteMovieById(id)

       return if(deleted){
           BaseResponse.SuccessResponse(null,RespondMessages.SUCCESS)
       }else{
           BaseResponse.ErrorResponse(RespondMessages.GENERAL_ERROR)
       }
    }

    override suspend fun updateTitlesOfMovie(movieId: Long, movieTitles: List<TitleArgs>): BaseResponse {
        val titles = movieTitleService.create(movieId,movieTitles)

        return if(titles!=null){
            BaseResponse.SuccessResponse(ListOfTitles(titles), message = RespondMessages.SUCCESS)
        }
        else{
            BaseResponse.ErrorResponse(RespondMessages.GENERAL_ERROR)
        }
    }

    override suspend fun updateSynopsisOfMovie(movieId: Long, movieSynopsis: List<SynopsisArgs>): BaseResponse {
        val synopsis = movieSynopsisService.create(movieId,movieSynopsis)

        return if(synopsis!=null){
            BaseResponse.SuccessResponse(ListOfSynopsis(synopsis), message = RespondMessages.SUCCESS)
        }
        else{
            BaseResponse.ErrorResponse(message = RespondMessages.GENERAL_ERROR)
        }
    }

    override suspend fun updateFramesOfMovie(movieId: Long, movieFrames: List<FrameArgs>): BaseResponse {
        val frames = movieFramesService.create(movieId,movieFrames)

        return if(frames!=null){
            BaseResponse.SuccessResponse(ListOfFrames(frames), message = RespondMessages.SUCCESS)
        }
        else{
            BaseResponse.ErrorResponse(message = RespondMessages.GENERAL_ERROR)
        }
    }

    override suspend fun updateVideosOfMovie(movieId: Long, movieVideos: List<VideoArgs>): BaseResponse {
        val videos = movieVideosService.create(movieId, listOfVideos = movieVideos)

        return if(videos!=null){
            BaseResponse.SuccessResponse(ListOfVideos(videos), message = RespondMessages.SUCCESS)
        }
        else{
            BaseResponse.ErrorResponse(message = RespondMessages.GENERAL_ERROR)
        }
    }


    override suspend fun getFramesOfMovie(movieId: Long): BaseResponse {
        val frames = movieFramesService.getAllFramesOfMovie(movieId)

        return BaseResponse.SuccessResponse(ListOfFrames(frames),RespondMessages.SUCCESS)
    }

    override suspend fun getVideosOfMovies(movieId: Long): BaseResponse {
        val videos = movieVideosService.getAllMovieVideos(movieId)

        return BaseResponse.SuccessResponse(ListOfVideos(videos),RespondMessages.SUCCESS)
    }


    override suspend fun updateMovieTrailer(movieId: Long, trailerUrl: String): BaseResponse {
        val updated = movieDataService.updateTrailerToMovie(movieId,trailerUrl)

        return if(updated){
            BaseResponse.SuccessResponse(data = null ,message = RespondMessages.SUCCESS)
        }
        else{
            BaseResponse.ErrorResponse(message = RespondMessages.GENERAL_ERROR)
        }
    }
}