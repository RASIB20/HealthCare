package com.example.healthcare

import Database
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.SimpleAdapter

class OrderDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)

        var order_details: Array<Array<String>> = emptyArray()
        var item: HashMap<String, String> = hashMapOf()
        var sa: SimpleAdapter? = null
        var lst: ListView = findViewById(R.id.listViewOD)
        var backbtn: Button = findViewById(R.id.backButtonOD)
        var list = ArrayList<HashMap<String, String>>()

        backbtn!!.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
        }

        val db:Database = Database(this,"healthcare",null,1)
        var shared:SharedPreferences = getSharedPreferences("healthcare", MODE_PRIVATE)
        var username:String = shared.getString("userName","").toString()
        var dbData:ArrayList<String> = db.getOrderData(username)

        order_details = Array(dbData.size) { arrayOf<String>("", "", "", "", "") }
        for (i in order_details.indices) {
            val arrData = dbData[i].toString()
            val strData = arrData.split("\\$".toRegex())
            order_details[i][0] = strData[0]
            order_details[i][1] = strData[1]
            if (strData[7] == "medicine") {
                order_details[i][3] = "Del:${strData[4]}"
            } else {
                order_details[i][3] = "Del: ${strData[4]} ${strData[5]}"
            }
            order_details[i][2] = "Rs.${strData[6]}"
            order_details[i][4] = strData[7]
        }

        list = ArrayList()
        for(i in order_details.indices){
            item = HashMap<String,String>()
            item.put("line1",order_details[i][0].toString())
            item.put("line2",order_details[i][1].toString())
            item.put("line3",order_details[i][2].toString())
            item.put("line4",order_details[i][3].toString())
            item.put("line5",order_details[i][4].toString())
            list.add(item)
        }
        sa = SimpleAdapter(this,list,R.layout.multi_lines, arrayOf("lin1","line2","line3","line4","line5"),
            intArrayOf(R.id.line_a,R.id.line_b,R.id.line_c,R.id.line_d,R.id.line_e)
            )
        lst.adapter = sa

    }
}