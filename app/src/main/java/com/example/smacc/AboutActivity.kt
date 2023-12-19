package com.example.smacc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class AboutActivity : AppCompatActivity() {

    private lateinit var buttonBack : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)


        // Back Button
        buttonBack = findViewById(R.id.button_back)
        buttonBack.setOnClickListener {
            onBackPressed()
        }
    }
}