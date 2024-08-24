package com.example.memegenerator;

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.memegenerator.R
import com.example.memegenerator.api.RetrofitClient
import com.example.memegenerator.model.Meme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var spinner: Spinner
    private lateinit var topEditText: EditText
    private lateinit var btmEditText: EditText
    private lateinit var generateButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Ініціалізація UI елементів
        spinner = findViewById(R.id.memes_spinner)
        topEditText = findViewById(R.id.top_editTxt)
        btmEditText = findViewById(R.id.btm_editTxt)
        generateButton = findViewById(R.id.generate_btn)

        // Виклик API для отримання даних
        RetrofitClient.api.getMemes().enqueue(object : Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                if (response.isSuccessful) {
                    val memes = response.body()
                    Log.d("API Response", "Memes: $memes")  // Логування відповіді
                    val adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, memes ?: listOf())
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner.adapter = adapter
                } else {
                    Log.e("API Response", "Error: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                // Обробка помилки
                Log.e("API Error", "Error: ${t.message}")
                t.printStackTrace()
            }
        })

        // Налаштування кнопки
        generateButton.setOnClickListener {
            val selectedMeme = spinner.selectedItem.toString()
            val topText = topEditText.text.toString()
            val btmText = btmEditText.text.toString()

            // Логіка для генерації мемів або інші дії
            Log.d("Generate Button", "Selected Meme: $selectedMeme, Top Text: $topText, Bottom Text: $btmText")
        }
    }
}