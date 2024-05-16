package com.example.healthcare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class HealthArticlesDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health_articles_details)
        val title:TextView = findViewById(R.id.healthArticlesDetailsTitle)
        val backbtn:Button = findViewById(R.id.healthArticlesDetailsBackButton)
        val img:ImageView = findViewById(R.id.healthArticlesDetailsimageView)

        var t =intent.getStringExtra("text1").toString()
        title.setText("")
        title.setText(t)

        val bundle = intent.extras
        if (bundle != null) {
            val resId = bundle.getInt("text2")
            img.setImageResource(resId)
        }

        backbtn.setOnClickListener {
            startActivity(Intent(this,HealthArticles::class.java))
        }

    }
}