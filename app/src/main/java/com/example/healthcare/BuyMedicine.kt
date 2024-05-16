package com.example.healthcare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.SimpleAdapter

class BuyMedicine : AppCompatActivity() {
    private val packages = arrayOf(
        arrayOf("Uprise-03 1000IU Capsule", "", "", "", "50"),
        arrayOf("Panadol", "", "", "", "20"),
        arrayOf("Amoxicillin", "", "", "", "30"),
        arrayOf("Lipitor", "", "", "", "25"),
        arrayOf("Zyrtec", "", "", "", "40"),
        arrayOf("Nexium", "", "", "", "35"),
        arrayOf("Advil", "", "", "", "15"),
        arrayOf("Synthroid", "", "", "", "45"),
        arrayOf("Metformin", "", "", "", "28")
    )

    private val packageDetails = arrayOf(
        "Building and keeping the bones & teeth strong\n" +
                "Reducing Fatigue/stress and muscular pains\n" +
                "Boosting immunity and increasing resistance against infections\n",
        // Additional benefits for Panadol
        "Relief from mild to moderate pain such as headache, toothache, and musculoskeletal conditions\n" +
                "Reduction of fever\n",
        // Additional benefits for Amoxicillin
        "Treatment of bacterial infections such as pneumonia, bronchitis, and infections of the ear, nose, throat, skin, or urinary tract\n" +
                "Prevention of infections before dental or medical procedures\n",
        // Additional benefits for Lipitor
        "Lowering levels of 'bad' cholesterol (low-density lipoprotein, or LDL) and triglycerides in the blood\n" +
                "Increasing levels of 'good' cholesterol (high-density lipoprotein, or HDL)\n",
        // Additional benefits for Zyrtec
        "Relief from allergy symptoms such as sneezing, runny nose, itching, and watery eyes\n" +
                "Treatment of itching and swelling caused by chronic urticaria (hives)\n",
        // Additional benefits for Nexium
        "Healing of erosive esophagitis and maintenance of healed erosive esophagitis\n" +
                "Treatment of symptoms of gastroesophageal reflux disease (GERD)\n",
        // Additional benefits for Advil
        "Relief from pain, inflammation, and fever\n" +
                "Treatment of menstrual cramps, dental pain, and sports injuries\n",
        // Additional benefits for Synthroid
        "Treatment of hypothyroidism (low thyroid hormone levels)\n" +
                "Prevention of goiter (enlarged thyroid gland) and thyroid cancer\n",
        // Additional benefits for Metformin
        "Treatment of type 2 diabetes mellitus\n" +
                "Improvement of insulin sensitivity and lowering of blood sugar levels\n"
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_medicine)
     //   var order_details: Array<Array<String>> = emptyArray()
        var item: HashMap<String, String> = hashMapOf()
        var sa: SimpleAdapter? = null
        var lst: ListView = findViewById(R.id.buyMedicenListView)
        var backbtn: Button = findViewById(R.id.buyMedicenBackButton)
        var gotocartbtn:Button = findViewById(R.id.buyMedicenGoToCartButton)
        var list = ArrayList<HashMap<String, String>>()

        backbtn.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
        }
        gotocartbtn.setOnClickListener {
            startActivity(Intent(this,CartBuyMedicine::class.java))
        }

        for(i in packages.indices){
            item = HashMap<String,String>()
            item.put("line1",packages[i][0])
            item.put("line2",packages[i][1])
            item.put("line3",packages[i][2])
            item.put("line4",packages[i][3])
            item.put("line5","Total Cost: "+packages[i][4])
            list.add(item)
        }

        sa = SimpleAdapter(this,list,R.layout.multi_lines, arrayOf("line1","line2","line3","line4","line5"),
            intArrayOf(R.id.line_a,R.id.line_b,R.id.line_c,R.id.line_d,R.id.line_e))
        lst.adapter = sa

        lst.setOnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(this,BuyMedcineDetails::class.java)
            intent.putExtra("text1", packages[i][0])
            intent.putExtra("text2", packageDetails[i])
            intent.putExtra("text3", packages[i][4])
            startActivity(intent)
        }

    }
}