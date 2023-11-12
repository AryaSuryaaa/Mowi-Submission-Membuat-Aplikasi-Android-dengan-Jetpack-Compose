package com.aryasurya.mowy.remote.data

import com.aryasurya.mowy.remote.response.DetailMovie
import com.aryasurya.mowy.remote.response.MovieResponse
import com.aryasurya.mowy.remote.response.SearchMovieResponse
import com.aryasurya.mowy.remote.response.VideoResultsItem
import com.aryasurya.mowy.remote.response.VideoYoutube
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    suspend fun getPopularMovie(
    ): MovieResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovie(
    ): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getDetailMovie(
        @Path("movie_id") movieId: Int
    ): DetailMovie

    @GET("movie/{movie_id}/videos")
    suspend fun getVideoMovie(
        @Path("movie_id") movieId: Int
    ): VideoYoutube

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") query: String,
        @Query("include_adult") includeAdult: Boolean,
    ): SearchMovieResponse
}