package com.example.memegenerator.repository

import com.example.memegenerator.datasource.RemoteDataSource

class MemeRepository(private val remoteDataSource: RemoteDataSource) {

    fun getMemes(callback: (List<String>?) -> Unit) {
        remoteDataSource.fetchMemes(callback)
    }

    fun createMeme(meme: String, topText: String, bottomText: String, callback: (ByteArray?) -> Unit) {
        remoteDataSource.generateMeme(meme, topText, bottomText) { responseBody ->
            val byteArray = responseBody?.bytes()
            callback(byteArray)
        }
    }
}
