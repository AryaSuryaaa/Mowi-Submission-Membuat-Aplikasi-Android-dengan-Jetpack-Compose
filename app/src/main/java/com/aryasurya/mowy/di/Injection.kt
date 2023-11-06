package com.aryasurya.mowy.di

import android.content.Context
import com.aryasurya.mowy.remote.MovieRepository
import com.aryasurya.mowy.remote.data.ApiConfig


object Injection {
    fun provideRepository(): MovieRepository {
        val apiService = ApiConfig.getApiService()
        return MovieRepository.getInstance(apiService)
    }
}