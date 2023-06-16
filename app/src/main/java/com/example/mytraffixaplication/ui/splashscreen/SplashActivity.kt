package com.example.mytraffixaplication.ui.splashscreen

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.mytraffixaplication.R
import com.example.mytraffixaplication.ui.login.LoginActivity
import com.example.mytraffixaplication.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var sharedPref: SharedPreferences
    private val SPLASH_TIMEOUT = 2000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val isLoggedIn = checkLoginStatus()

            if (isLoggedIn) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, SPLASH_TIMEOUT)
    }

    private fun checkLoginStatus(): Boolean {
        val jwtToken = getJwtTokenFromPreferences()
        return !jwtToken.isNullOrEmpty()
    }

    private fun getJwtTokenFromPreferences(): String? {
        sharedPref = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        return sharedPref.getString("jwt_token", "")
    }
}