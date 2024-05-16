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

class LabTestDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab_test_detail)

        val tvPackageName:TextView = findViewById(R.id.textViewBMCartPackageName)
        val tvTotalCost:TextView = findViewById(R.id.totalCostBMCart)
        val edDetails:EditText = findViewById(R.id.listViewBMCart)
        val gotoCart:Button = findViewById(R.id.backBMCartButton)
        val back:Button = findViewById(R.id.checkOutBMCartButton)
        val price:String = intent.getStringExtra("price").toString()
        tvTotalCost.setText(price)

        back.setOnClickListener {
            startActivity(Intent(this,LabTest::class.java))
        }

        gotoCart.setOnClickListener {
            val sharedpref:SharedPreferences = getSharedPreferences("healthcare", MODE_PRIVATE)
            val userName:String = sharedpref.getString("userName","").toString()
            val product:String = tvPackageName.text.toString()
            val price: Float = intent.getStringExtra("text3").toString().toFloat()

            val db:Database = Database(this,"healthcare",null,1)
            if(db.checkCart(userName, product)==1){
                Toast.makeText(this,"Product already added",Toast.LENGTH_SHORT).show()
            }else{
                db.addCart(userName,product,price,"lab")
                Toast.makeText(this,"Record inserted in Cart",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,LabTest::class.java))
            }
        }

        edDetails.keyListener = null


        tvPackageName.text = intent.getStringExtra("text1")
        edDetails.setText(intent.getStringExtra("text2"))
        tvTotalCost.text = "Total Cost : "+intent.getStringExtra("text3")+"/-"

    }
}