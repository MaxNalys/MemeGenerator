// SecondActivity.kt
package com.example.memegenerator.ui

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.memegenerator.R

class SecondActivity : AppCompatActivity() {

    private lateinit var memeImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_page)

        memeImageView = findViewById(R.id.memeImageView)

        // Retrieve the byte array from intent and convert it to Bitmap
        val byteArray = intent.getByteArrayExtra("MEME_BITMAP")
        byteArray?.let {
            val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
            memeImageView.setImageBitmap(bitmap)
        } ?: run {
            // Handle case when byteArray is null
        }
    }
}
