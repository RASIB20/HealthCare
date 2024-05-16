package com.example.healthcare

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Delay for 2 seconds and then launch another activity
        Handler(Looper.getMainLooper()).postDelayed({
            // Start your next activity here
            val intent = Intent(this, LoginActivty::class.java)
            startActivity(intent)
            finish() // Finish this activity so the user can't come back to it with the back button
        }, 4000) // 2000 milliseconds = 2 seconds
    }
}
