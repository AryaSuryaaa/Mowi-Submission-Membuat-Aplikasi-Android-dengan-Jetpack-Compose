package com.aryasurya.mowy.remote.data

import com.aryasurya.mowy.remote.response.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    suspend fun getPopularMovie(
    ): MovieResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovie(
    ): MovieResponse

    @GET("movie/top_rated")
    suspend fun getNowPlayingMovie(
        @Query("page") page: Int
    ): MovieResponse
}