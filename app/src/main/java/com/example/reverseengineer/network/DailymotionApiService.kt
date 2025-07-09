package com.example.reverseengineer.network

import com.example.reverseengineer.data.DailymotionVideoMetadata
import retrofit2.http.GET
import retrofit2.http.Path

interface DailymotionApiService {
    @GET("player/metadata/video/{videoId}")
    suspend fun getVideoMetadata(@Path("videoId") videoId: String): DailymotionVideoMetadata
} 