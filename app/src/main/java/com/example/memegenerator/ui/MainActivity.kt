// MainActivity.kt
package com.example.memegenerator.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.memegenerator.R
import com.example.memegenerator.api.RetrofitClient
import com.example.memegenerator.datasource.RemoteDataSource
import com.example.memegenerator.repository.MemeRepository
import com.example.memegenerator.viewmodel.MemeViewModel
import com.example.memegenerator.viewmodel.MemeViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var spinner: Spinner
    private lateinit var topEditText: EditText
    private lateinit var btmEditText: EditText
    private lateinit var generateButton: Button

    private lateinit var memeViewModel: MemeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Initialize UI elements
        spinner = findViewById(R.id.memes_spinner)
        topEditText = findViewById(R.id.top_editTxt)
        btmEditText = findViewById(R.id.btm_editTxt)
        generateButton = findViewById(R.id.generate_btn)

        // Initialize dependencies
        val apiService = RetrofitClient.apiService
        val remoteDataSource = RemoteDataSource(apiService)
        val repository = MemeRepository(remoteDataSource)
        val viewModelFactory = MemeViewModelFactory(repository)

        memeViewModel = ViewModelProvider(this, viewModelFactory).get(MemeViewModel::class.java)

        // Observe memes LiveData
        memeViewModel.memes.observe(this) { memeNames ->
            if (memeNames != null && memeNames.isNotEmpty()) {
                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item,
                    memeNames
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            } else {
                // Handle empty or null list
            }
        }

        // Observe generatedMeme LiveData
        memeViewModel.generatedMeme.observe(this) { memeBitmap ->
            memeBitmap?.let {
                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("MEME_BITMAP", it)
                startActivity(intent)
            } ?: run {
                // Handle null bitmap (error case)
            }
        }

        // Fetch meme names
        memeViewModel.fetchMemes()

        // Set up button click listener
        generateButton.setOnClickListener {
            val selectedMeme = spinner.selectedItem?.toString()
            val topText = topEditText.text.toString()
            val btmText = btmEditText.text.toString()

            if (!selectedMeme.isNullOrEmpty()) {
                memeViewModel.generateMeme(selectedMeme, topText, btmText)
            } else {
                // Handle case when no meme is selected
            }
        }
    }
}
