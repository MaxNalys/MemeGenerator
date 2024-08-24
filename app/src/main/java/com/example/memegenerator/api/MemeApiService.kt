package com.example.memegenerator.api

import com.example.memegenerator.model.Meme
import retrofit2.Call
import retrofit2.http.GET

interface MemeApiService {
    @GET("images")
    fun getMemes(): Call<List<String>> // Змінюємо на List<String>
}