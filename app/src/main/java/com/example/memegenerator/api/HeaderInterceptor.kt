package com.example.memegenerator.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request().newBuilder()
            .addHeader("x-rapidapi-key", "99ce56a1a0msha23dcce4e8f1a70p17f625jsn0e0d7f49bc37")
            .addHeader("x-rapidapi-host", "ronreiter-meme-generator.p.rapidapi.com")
            .build()
        return chain.proceed(request)
    }
}
