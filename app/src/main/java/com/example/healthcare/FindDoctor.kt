package com.example.healthcare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.cardview.widget.CardView

class FindDoctor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_doctor)

        val backbtn:CardView=findViewById(R.id.cardFDBack)
        backbtn.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
        }

        val familyphysician:CardView = findViewById(R.id.cardFDFamilyPhysician)
        familyphysician.setOnClickListener {
            val intent:Intent= Intent(this,DoctorDetailsActivity::class.java)
            intent.putExtra("title","Family Physician")
            startActivity(intent)
        }

        val dietician:CardView = findViewById(R.id.cardFDdietician)
        dietician.setOnClickListener {
            val intent:Intent= Intent(this,DoctorDetailsActivity::class.java)
            intent.putExtra("title","Dietician")
            startActivity(intent)
        }

        val dentist:CardView = findViewById(R.id.cardFDDentist)
        dentist.setOnClickListener {
            val intent:Intent= Intent(this,DoctorDetailsActivity::class.java)
            intent.putExtra("title","Dentist")
            startActivity(intent)
        }

        val surgeon:CardView = findViewById(R.id.cardFDSurgeon)
        surgeon.setOnClickListener {
            val intent:Intent= Intent(this,DoctorDetailsActivity::class.java)
            intent.putExtra("title","Surgeon")
            startActivity(intent)
        }

        val cardiologist:CardView = findViewById(R.id.cardFDCardiologist)
        cardiologist.setOnClickListener {
            val intent:Intent= Intent(this,DoctorDetailsActivity::class.java)
            intent.putExtra("title","Cardiologist")
            startActivity(intent)
        }
    }
}