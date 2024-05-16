package com.example.healthcare

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DoctorDetailsActivity : AppCompatActivity() {
    private val TAG = "DoctorDetailsActivity"

    private val doctorDetails1 = arrayOf(
        arrayOf("Dr. Muhammad Ali", "Aga Khan University Hospital, Karachi", "Mobile:021-34567890", "1500"),
        arrayOf("Dr. Shahid Khan", "Shaukat Khanum Memorial Cancer Hospital, Lahore", "Mobile:042-32567890", "1200"),
        arrayOf("Dr. Saira Waqar", "Pakistan Institute of Medical Sciences, Islamabad", "Mobile:051-9260789", "1000"),
        arrayOf("Dr. Ahmed Hassan", "Jinnah Hospital, Lahore", "Mobile:042-111-000-786", "800"),
        arrayOf("Dr. Uzma Rasheed", "Ziauddin Hospital, Karachi", "Mobile:021-35862911", "1800"),
        arrayOf("Dr. Farhan Khan", "Khyber Teaching Hospital, Peshawar", "Mobile:091-5852222", "1200")
    )

    private val doctorDetails2 = arrayOf(
        arrayOf("Dr. Muhammad Ali", "Aga Khan University Hospital, Karachi", "Mobile:021-34567890", "1500"),
        arrayOf("Dr. Shahid Khan", "Shaukat Khanum Memorial Cancer Hospital, Lahore", "Mobile:042-32567890", "1200"),
        arrayOf("Dr. Saira Waqar", "Pakistan Institute of Medical Sciences, Islamabad", "Mobile:051-9260789", "1000"),
        arrayOf("Dr. Ahmed Hassan", "Jinnah Hospital, Lahore", "Mobile:042-111-000-786", "800"),
        arrayOf("Dr. Uzma Rasheed", "Ziauddin Hospital, Karachi", "Mobile:021-35862911", "1800"),
        arrayOf("Dr. Farhan Khan", "Khyber Teaching Hospital, Peshawar", "Mobile:091-5852222", "1200")
    )

    private val doctorDetails3 = arrayOf(
        arrayOf("Dr. Muhammad Ahmed", "Smile Dental Care, Karachi", "Mobile:021-34567890", "500"),
        arrayOf("Dr. Sana Ali", "Dental Care Center, Lahore", "Mobile:042-32567890", "400"),
        arrayOf("Dr. Amina Khan", "Pakistan Dental Council, Islamabad", "Mobile:051-9260789", "450"),
        arrayOf("Dr. Farhan Saeed", "Dental Hospital, Peshawar", "Mobile:091-5852222", "350"),
        arrayOf("Dr. Uzma Rasheed", "Ziauddin Hospital, Karachi", "Mobile:021-35862911", "550"),
        arrayOf("Dr. Ahmad Hassan", "Services Hospital, Lahore", "Mobile:042-99204736", "400")
    )

    private val doctorDetails4 = arrayOf(
        arrayOf("Dr. Muhammad Ali", "Aga Khan University Hospital, Karachi", "Mobile:021-34567890", "1500"),
        arrayOf("Dr. Shahid Khan", "Shaukat Khanum Memorial Cancer Hospital, Lahore", "Mobile:042-32567890", "1200"),
        arrayOf("Dr. Saira Waqar", "Pakistan Institute of Medical Sciences, Islamabad", "Mobile:051-9260789", "1000"),
        arrayOf("Dr. Ahmed Hassan", "Jinnah Hospital, Lahore", "Mobile:042-111-000-786", "900"),
        arrayOf("Dr. Uzma Rasheed", "Ziauddin Hospital, Karachi", "Mobile:021-35862911", "1800"),
        arrayOf("Dr. Farhan Khan", "Khyber Teaching Hospital, Peshawar", "Mobile:091-5852222", "1200")
    )

    private val doctorDetails5 = arrayOf(
        arrayOf("Dr. Muhammad Ahmed", "National Institute of Cardiovascular Diseases, Karachi", "Mobile:021-34567890", "1200"),
        arrayOf("Dr. Sana Ali", "Punjab Institute of Cardiology, Lahore", "Mobile:042-32567890", "1000"),
        arrayOf("Dr. Amina Khan", "Pakistan Cardiac Society, Islamabad", "Mobile:051-9260789", "1100"),
        arrayOf("Dr. Farhan Saeed", "Cardiac Hospital, Peshawar", "Mobile:091-5852222", "900"),
        arrayOf("Dr. Uzma Rasheed", "Ziauddin Hospital, Karachi", "Mobile:021-35862911", "1300"),
        arrayOf("Dr. Ahmad Hassan", "Services Hospital, Lahore", "Mobile:042-99204736", "1000")
    )

    var doctorDetails = arrayOf<Array<String>>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_details)

        Log.d(TAG, "onCreate: Activity created") // Log activity creation

        val name: String? = intent.getStringExtra("title")
        val title: TextView = findViewById(R.id.DDName)
        val btn: Button = findViewById(R.id.DDbackButton)
        val list = ArrayList<HashMap<String, String>>()

        title.text = name ?: ""
        Log.d(TAG, "onCreate: Title set to $name")

        btn.setOnClickListener {
            startActivity(Intent(this, FindDoctor::class.java))
        }

        doctorDetails = when (name) {
            "Family Physician" -> doctorDetails1
            "Dietician" -> doctorDetails2
            "Dentist" -> doctorDetails3
            "Surgeon" -> doctorDetails4
            else -> doctorDetails5
        }

        for (i in doctorDetails.indices) {
            val item = HashMap<String, String>()
            item["line1"] = doctorDetails[i][0]
            item["line2"] = doctorDetails[i][1]
            item["line3"] = doctorDetails[i][2]
            item["line4"] = ""
            item["line5"] = "Cons Fees: " + doctorDetails[i][3]
            list.add(item)
        }


        val sa: SimpleAdapter = SimpleAdapter(
            this,
            list,
            R.layout.multi_lines,
            arrayOf("line1", "line2", "line3", "line4", "line5"),
            intArrayOf(R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e)
        )

        val lst: ListView = findViewById(R.id.DDListView)
        lst.adapter = sa

        lst.setOnItemClickListener { adapterView, view, i, l ->

            val intent = Intent(this, BookApointment::class.java)

            intent.putExtra("text1", title.text.toString())
            intent.putExtra("name", doctorDetails[i][0])
            intent.putExtra("address", doctorDetails[i][1])
            intent.putExtra("contact", doctorDetails[i][2])
            intent.putExtra("fees", doctorDetails[i][3])
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: Activity started")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: Activity resumed")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: Activity paused")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: Activity stopped")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: Activity destroyed")
    }
}
