package com.example.healthcare

import Database
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class BuyMedicineBook : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_medicine_book)

        val edname: EditText = findViewById(R.id.BMBUserName)
        val edaddress: EditText = findViewById(R.id.BMBaddress)
        val edcontact: EditText = findViewById(R.id.BMBcontact)
        val edpincode: EditText = findViewById(R.id.BMBpinCode)
        val bookbtn: Button = findViewById(R.id.BMBBookbookbutton)

        val date:String = intent.getStringExtra("date").toString()
        val price = intent.getStringExtra("price")?.toString()?.split("\\$".toRegex())

        bookbtn.setOnClickListener {
            val shared: SharedPreferences = getSharedPreferences("healthcare", MODE_PRIVATE)
            val userName = shared.getString("userName","").toString()
            val db:Database = Database(this,"healthcare",null,1)
            db.addOrder(userName,"medicine",edname.text.toString(),edaddress.text.toString(),edcontact.text.toString(),edpincode.text.toString().toInt(),date.toString(),"",price.toString().toFloat())
            db.removeCart(userName,"medicine")
            Toast.makeText(this,"Your booking is done successfully", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,HomeActivity::class.java))
        }
    }
}