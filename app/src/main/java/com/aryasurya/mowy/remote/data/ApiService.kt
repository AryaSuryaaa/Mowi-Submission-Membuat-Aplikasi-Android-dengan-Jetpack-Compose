package com.aryasurya.mowy.remote.data

import com.aryasurya.mowy.remote.response.MovieResponse
import retrofit2.http.GET

interface ApiService {
    @GET("movie/popular")
    suspend fun getPopularMovie(
    ): MovieResponse
}