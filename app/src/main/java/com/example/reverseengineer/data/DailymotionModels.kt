package com.example.reverseengineer.data

import com.google.gson.annotations.SerializedName

data class DailymotionVideoMetadata(
    val title: String?,
    val description: String?,
    val duration: Int?,
    val owner: Owner?
)

data class Owner(
    val username: String?
) 