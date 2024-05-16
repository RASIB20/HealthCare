package com.example.healthcare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.SimpleAdapter

class HealthArticles : AppCompatActivity() {
    val healthDetails = arrayOf(
        arrayOf("Eating vegetables daily", "", "", "", "Click for More Details"),
        arrayOf("Regular exercise rouxtine", "", "", "", "Click for More Details"),
        arrayOf("Stay hydrated", "", "", "", "Click for More Details"),
        arrayOf("Healthy sleeping habits", "", "", "", "Click for More Details")
    )

    var images: IntArray = intArrayOf(
        R.drawable.eatingvegesdaily,
        R.drawable.health1,
        R.drawable.stayhydrated,
        R.drawable.sleeping
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health_articles)

        var item: HashMap<String, String>? = null
        var list: ArrayList<HashMap<String, String>>? = null
        var sa: SimpleAdapter? = null
        val backbtn:Button = findViewById(R.id.healthArticlesBackButton)
        val lst:ListView = findViewById(R.id.healthArticlesListView)
        backbtn.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
        }

        list = ArrayList()
        for(i in healthDetails.indices){
            item = HashMap<String,String>()
            item.put("line1",healthDetails[i][0])
            item.put("line2",healthDetails[i][1])
            item.put("line3",healthDetails[i][2])
            item.put("line4",healthDetails[i][3])
            list.add(item)
        }
        sa = SimpleAdapter(this,list,R.layout.multi_lines, arrayOf("line1","line2","line3","line4"),
            intArrayOf(R.id.line_a,R.id.line_b,R.id.line_c,R.id.line_d))
        lst.adapter = sa

        lst.setOnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(this,HealthArticlesDetails::class.java)
            intent.putExtra("text1",healthDetails[i][0])
            intent.putExtra("text2",images[i])
            startActivity(intent)
        }
    }
}