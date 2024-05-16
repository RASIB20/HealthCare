package com.example.healthcare

import Database

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.TextView
import java.util.Calendar

class labCart : AppCompatActivity() {
    var datepickerdialog: DatePickerDialog? = null
    var timepickerdialog: TimePickerDialog? = null
    var dateBtn: Button? = null
    var timeBtn: Button? = null
    private var packages: Array<Array<String>> = emptyArray()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab_cart)
        var item: HashMap<String, String>? = null
        var list: ArrayList<HashMap<String, String>>? = null
        val lst:ListView = findViewById(R.id.listViewCart)
        var sa: SimpleAdapter? = null
        var tvTotal: TextView = findViewById(R.id.totalCostCart)

        val shared:SharedPreferences = getSharedPreferences("healthcare", MODE_PRIVATE)
        val userName:String = shared.getString("userName","").toString()

        val db:Database = Database(this,"healthcare",null,1)
        var totalAmount:Float = 0F
        var dbData:ArrayList<String> =db.getCartData(userName,"lab")

        packages = Array(dbData.size)
        { Array(5) { "" } }

        dateBtn = findViewById(R.id.buttonCartDatePicker)
        timeBtn = findViewById(R.id.buttonCartTimePIcker)
        var btnCheckOut: Button = findViewById(R.id.checkOutCartButton)
        var btnBack: Button = findViewById(R.id.backCartButton)
        var pricee:Float=0.0F
        for (i in dbData.indices) {
            val arrData = dbData[i].toString()
            val strData = arrData.split("\\$".toRegex())
            packages[i][0] = strData[0]
            packages[i][4] = "Cost: ${strData[1]}/-"
            totalAmount += strData[1].toFloat()
            pricee=totalAmount
        }
        tvTotal.setText("Total Cost: "+totalAmount)



        list=ArrayList()
        for(i in packages.indices){
            item = HashMap<String,String>()
            item.put("line1",packages[i][0])
            item.put("line2",packages[i][1])
            item.put("line3",packages[i][2])
            item.put("line4",packages[i][3])
            item.put("line5",packages[i][4])
            list.add(item)
        }

        sa = SimpleAdapter(this,list,R.layout.multi_lines, arrayOf("line1","line2","line3","line4","line5"),
            intArrayOf(R.id.line_a,R.id.line_b,R.id.line_c,R.id.line_d,R.id.line_e)
        )
        lst.adapter = sa

        btnBack.setOnClickListener {
            startActivity(Intent(this,LabTest::class.java))
        }
        btnCheckOut.setOnClickListener {

            val intent:Intent = Intent(this,LabTestBook::class.java)
            intent.putExtra("price",pricee.toString())
            intent.putExtra("date",dateBtn!!.text.toString())
            intent.putExtra("time",timeBtn!!.text.toString())
            startActivity(intent)
        }

        initDatepicker()
        dateBtn!!.setOnClickListener {
            datepickerdialog!!.show()
        }

        initTimePicker()
        timeBtn!!.setOnClickListener {
            timepickerdialog!!.show()
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