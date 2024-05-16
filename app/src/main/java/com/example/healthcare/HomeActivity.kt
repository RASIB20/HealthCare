package com.example.healthcare

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val sharedPref : SharedPreferences = getSharedPreferences("healthcare", MODE_PRIVATE)
        val userName: String? =sharedPref.getString("userName","")
        Toast.makeText(this,"Welcome "+userName,Toast.LENGTH_SHORT).show()

        val exitCard:CardView=findViewById(R.id.exit)
        exitCard.setOnClickListener {
            val auth=FirebaseAuth.getInstance()
            auth.signOut()
            val edtior = sharedPref.edit()
            edtior.clear()
            edtior.apply()
            startActivity(Intent(this,LoginActivty::class.java))
        }

        val findDoctor:CardView=findViewById(R.id.finddoctor)
        findDoctor.setOnClickListener {
            startActivity(Intent(this,FindDoctor::class.java))
        }

        val labTest:CardView = findViewById(R.id.labtest)
        labTest.setOnClickListener {
            startActivity(Intent(this,LabTest::class.java))
        }

        val orderDetails:CardView = findViewById(R.id.orderdetails)
        orderDetails.setOnClickListener {
            startActivity(Intent(this,OrderDetails::class.java))
        }

        val buymedicine:CardView = findViewById(R.id.buyMedicen)
        buymedicine.setOnClickListener {
            startActivity(Intent(this,BuyMedicine::class.java))
        }

        val healthdoctor:CardView = findViewById(R.id.healthdoctor)
        healthdoctor.setOnClickListener {
            startActivity(Intent(this,HealthArticles::class.java))
        }

    }
}