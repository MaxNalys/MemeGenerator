package com.example.memegenerator

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.memegenerator.R
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.output.ByteArrayOutputStream
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.InputStream

class MainActivity : AppCompatActivity() {

    private lateinit var spinner: Spinner
    private lateinit var topEditText: EditText
    private lateinit var btmEditText: EditText
    private lateinit var generateButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Initialize UI elements
        spinner = findViewById(R.id.memes_spinner)
        topEditText = findViewById(R.id.top_editTxt)
        btmEditText = findViewById(R.id.btm_editTxt)
        generateButton = findViewById(R.id.generate_btn)

        // Fetch meme names
        RetrofitClient.api.getMemes().enqueue(object : Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                if (response.isSuccessful) {
                    val memeNames = response.body()
                    if (memeNames != null) {
                        val adapter = ArrayAdapter(
                            this@MainActivity,
                            android.R.layout.simple_spinner_item,
                            memeNames
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spinner.adapter = adapter
                    } else {
                        Log.e("API Error", "Response body is null")
                    }
                } else {
                    Log.e("API Error", "Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                Log.e("API Error", "Failure: ${t.message}")
                t.printStackTrace()
            }
        })

        // Set up button click listener
        generateButton.setOnClickListener {
            val selectedMeme = spinner.selectedItem?.toString()
            val topText = topEditText.text.toString()
            val btmText = btmEditText.text.toString()

            if (selectedMeme != null) {
                RetrofitClient.api.generateMeme(selectedMeme, topText, btmText).enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            val inputStream = response.body()?.byteStream()
                            val bitmap = BitmapFactory.decodeStream(inputStream)
                            if (bitmap != null) {
                                val intent = Intent(this@MainActivity, SecondActivity::class.java)
                                // Convert bitmap to byte array to pass via intent
                                val byteArrayOutputStream = ByteArrayOutputStream()
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                                val byteArray = byteArrayOutputStream.toByteArray()
                                intent.putExtra("MEME_BITMAP", byteArray)
                                startActivity(intent)
                            } else {
                                Log.e("API Error", "Failed to decode bitmap")
                            }
                        } else {
                            Log.e("API Error", "Error: ${response.code()} - ${response.errorBody()?.string()}")
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.e("API Error", "Failure: ${t.message}")
                        t.printStackTrace()
                    }
                })
            } else {
                Log.e("Spinner Error", "No meme selected from spinner")
            }
        }
    }
}