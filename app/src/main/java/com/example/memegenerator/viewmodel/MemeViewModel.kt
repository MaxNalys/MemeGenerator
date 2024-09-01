package com.example.memegenerator.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.memegenerator.repository.MemeRepository

class MemeViewModel(private val repository: MemeRepository) : ViewModel() {

    private val _memes = MutableLiveData<List<String>>()
    val memes: LiveData<List<String>> get() = _memes

    private val _generatedMeme = MutableLiveData<ByteArray?>()
    val generatedMeme: LiveData<ByteArray?> get() = _generatedMeme

    fun fetchMemes() {
        repository.getMemes { memeList ->
            _memes.postValue(memeList)
        }
    }

    fun generateMeme(meme: String, topText: String, bottomText: String) {
        repository.createMeme(meme, topText, bottomText) { byteArray ->
            _generatedMeme.postValue(byteArray)
        }
    }
}
