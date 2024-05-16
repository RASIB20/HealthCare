package com.example.healthcare

import Database
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text

class BuyMedcineDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_medcine_details)

        val tvPackageName:TextView = findViewById(R.id.BuyMedicineDetailstextViewPackageName)
        val tvTotalCost:TextView = findViewById(R.id.BuyMedicineDetailstotalCost)
        val edDetails:EditText = findViewById(R.id.BuyMedicineDetailslistView)
        val btnBack:Button = findViewById(R.id.BuyMedicineDetailsbackButton)
        val gotoCart:Button = findViewById(R.id.BuyMedicineDetailscheckOutButton)
        tvPackageName.setText(intent.getStringExtra("text1"))
        edDetails.setText(intent.getStringExtra("text2"))
        tvTotalCost.setText("Total Cost: "+intent.getStringExtra("text3"))

        btnBack.setOnClickListener {
            startActivity(Intent(this,BuyMedicine::class.java))
        }

        gotoCart.setOnClickListener {
            val shared:SharedPreferences = getSharedPreferences("healthcare", MODE_PRIVATE)
            var username:String = shared.getString("userName","").toString()
            var product:String = intent.getStringExtra("text1").toString()
            var price:Float = intent.getStringExtra("text3").toString().toFloat()

            val db:Database = Database(this,"healthcare",null,1)
            if(db.checkCart(username,product)==1){
                Toast.makeText(this,"Product Already Added",Toast.LENGTH_SHORT).show()
            }else{
                db.addCart(username,product,price,"medicine")
                Toast.makeText(this,"Record inserted to cart",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,BuyMedicine::class.java))
            }
        }
    }
}