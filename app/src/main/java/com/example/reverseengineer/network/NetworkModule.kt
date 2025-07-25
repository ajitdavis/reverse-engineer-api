package com.example.reverseengineer.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {
    private const val BASE_URL = "https://www.dailymotion.com/"
    
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    val dailymotionApiService: DailymotionApiService by lazy {
        retrofit.create(DailymotionApiService::class.java)
    }
} 