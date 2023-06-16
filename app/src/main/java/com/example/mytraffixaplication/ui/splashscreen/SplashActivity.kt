package com.example.mytraffixaplication.ui.splashscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mytraffixaplication.R

class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIMEOUT = 2000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }
}