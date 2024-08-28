package com.example.memegenerator

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.memegenerator.R
import java.io.ByteArrayInputStream
import java.io.InputStream

class SecondActivity : AppCompatActivity() {

    private lateinit var memeImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_page)

        memeImageView = findViewById(R.id.memeImageView)

        // Retrieve the byte array from intent and convert it to Bitmap
        val byteArray = intent.getByteArrayExtra("MEME_BITMAP")
        if (byteArray != null) {
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            if (bitmap != null) {
                memeImageView.setImageBitmap(bitmap)
            } else {
                Log.e("SecondActivity", "Failed to decode bitmap")
            }
        } else {
            Log.e("SecondActivity", "Byte array is null")
        }
    }
}