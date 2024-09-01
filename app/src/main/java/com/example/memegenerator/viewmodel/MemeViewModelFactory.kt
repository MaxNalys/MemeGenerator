// MemeViewModelFactory.kt
package com.example.memegenerator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.memegenerator.repository.MemeRepository

class MemeViewModelFactory(private val repository: MemeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MemeViewModel::class.java)) {
            return MemeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}