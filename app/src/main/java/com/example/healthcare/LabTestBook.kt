package com.example.healthcare
import Database
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log // Import Log class
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.healthcare.R

class LabTestBook : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab_test_book)

        val edname:EditText = findViewById(R.id.labTestBookUserName)
        val edaddress:EditText = findViewById(R.id.labTestBookaddress)
        val edcontact:EditText = findViewById(R.id.labTestBookcontact)
        val edpincode:EditText = findViewById(R.id.labTestpinCode)
        val bookbtn:Button = findViewById(R.id.labTestBookbookbutton)

        val date:String = intent.getStringExtra("date").toString()
        val time:String = intent.getStringExtra("time").toString()
        var pricee = intent.getStringExtra("price").toString()

        bookbtn.setOnClickListener {
            val shared:SharedPreferences = getSharedPreferences("healthcare", MODE_PRIVATE)
            val userName = shared.getString("userName","").toString()
            val db:Database = Database(this,"healthcare",null,1)
            db.addOrder(userName,"lab",edname.text.toString(),edaddress.text.toString(),edcontact.text.toString(),edpincode.text.toString().toInt(),date.toString(),time.toString(),pricee.toFloat())
            db.removeCart(userName,"lab")
            Log.d("LabTestBook", "order removed from cart")
            Toast.makeText(this,"Your booking is done successfully",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,LabTest::class.java))
        }

    }
}
