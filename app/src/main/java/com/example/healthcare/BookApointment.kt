package com.example.healthcare

import Database
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text
import java.util.Calendar

class BookApointment : AppCompatActivity() {
    var timeBtn: Button? = null
    var dateBtn:Button ? = null
    var datepickerdialog: DatePickerDialog? = null
    var timepickerdialog: TimePickerDialog? = null
    private val TAG = "BookApointment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_apointment)

        Log.d(TAG, "onCreate: Activity created "+TAG) // Add logcat message

        val text1:TextView = findViewById(R.id.bookapointmenttitle)
        val text2:EditText = findViewById(R.id.bookapointmentUserName)
        val text3:EditText = findViewById(R.id.bookapointmentaddress)
        val text4:EditText = findViewById(R.id.bookapointmentcontact)
        val text5:EditText = findViewById(R.id.bookapointmentfees)
        val title:TextView = findViewById(R.id.bookapointmenttitle)
        val appointmentbtn:Button = findViewById(R.id.confirmapointmentbutton)
        val backbtn:Button=findViewById(R.id.backbutton)
        timeBtn = findViewById(R.id.buttonCartTimePIcker)
        dateBtn = findViewById(R.id.buttonBMCartDatePicker)

        text2.keyListener = null
        text3.keyListener = null
        text4.keyListener = null
        text5.keyListener = null

        text1.setText(intent.getStringExtra("text1").toString())
        text2.setText(intent.getStringExtra("name"))
        text3.setText(intent.getStringExtra("address"))
        text4.setText(intent.getStringExtra("contact"))
        text5.setText(intent.getStringExtra("fees"))

        initDatepicker()
        initTimePicker()

        timeBtn!!.setOnClickListener {
            timepickerdialog!!.show()
        }
        dateBtn!!.setOnClickListener {
            datepickerdialog!!.show()
        }

        backbtn.setOnClickListener {
            startActivity(Intent(this,DoctorDetailsActivity::class.java))
        }
        var name = text2.text.toString()
        var address = text3.text.toString()
        var contact = text4.text.toString()
        var fees = text5.text.toString()
        appointmentbtn.setOnClickListener {
            Log.d(TAG, "onCreate: Appointment button clicked")
            val db:Database = Database(this,"healthcare",null,1)
            Log.d(TAG, "onCreate: Database object created")
            val shared:SharedPreferences = getSharedPreferences("healthcare", MODE_PRIVATE)
            var username:String = shared.getString("userName","").toString()
            Log.d(TAG, "onCreate: shared Pref created and username accquired"+username)

            if(db.checkAppointmentExists(username,text2.text.toString(),text3.text.toString(),text4.text.toString(),dateBtn!!.text.toString(),timeBtn!!.text.toString())==1){
                Toast.makeText(this,"Appointment Already Exists",Toast.LENGTH_SHORT).show()
            }else{

                Log.d(TAG, "onCreate: Moved into else statement")
                db.addOrder(username,title.text.toString(),name,address,contact,0,dateBtn!!.text.toString(),timeBtn!!.text.toString(),fees.toFloat())
                Toast.makeText(this,"Your appointment is done succussfully",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,HomeActivity::class.java))

            }
        }

    }

    private fun initDatepicker() {
        var dateSetListener = DatePickerDialog.OnDateSetListener { datepicker: DatePicker?, i:Int, i1, i2 ->
            val newMonth = i1 - 1
            dateBtn!!.setText("$i2/$newMonth/$i")
        }
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val style = AlertDialog.THEME_HOLO_DARK
        datepickerdialog = DatePickerDialog(this, style, dateSetListener, year, month, day)
        datepickerdialog!!.datePicker.minDate = cal.timeInMillis + 86400000
    }

    private fun initTimePicker() {
        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, i, i1 ->
            // Do something with the selected time

            timeBtn!!.setText("$i:$i1")
        }

        val cal = Calendar.getInstance()
        val hours = cal.get(Calendar.HOUR_OF_DAY)
        val min = cal.get(Calendar.MINUTE)
        val style = AlertDialog.THEME_HOLO_DARK
        timepickerdialog = TimePickerDialog(this, style, timeSetListener, hours, min, true)
    }
}
