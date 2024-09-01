package com.example.memegenerator.datasource

import com.example.memegenerator.api.MemeApiService
import com.example.memegenerator.model.Meme
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource(private val apiService: MemeApiService) {

    fun fetchMemes(callback: (List<String>?) -> Unit) {
        apiService.getMemes().enqueue(object : Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun generateMeme(meme: String, topText: String, bottomText: String, callback: (ResponseBody?) -> Unit) {
        apiService.generateMeme(meme, topText, bottomText).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback(null)
            }
        })
    }
}
