package com.example.memegenerator.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MemeApiService {
    @GET("images")
    fun getMemes(): Call<List<String>> // For fetching meme names

    @GET("meme")
    fun generateMeme(
        @Query("meme") meme: String,
        @Query("top") topText: String,
        @Query("bottom") bottomText: String
    ): Call<ResponseBody> // For fetching generated meme as image
}
