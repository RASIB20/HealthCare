package com.example.healthcare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ListView
import android.widget.SimpleAdapter

class LabTest : AppCompatActivity() {

    private val TAG = "LabTestActivity"

    val packages = arrayOf(
        arrayOf("Package 1: Full Body Checkup", "", "", "", "999"),
        arrayOf("Package 2 Blood Glucose Fasting", "", "", "", "299"),
        arrayOf("Package 3: COVID-19 Antibody IgG", "", "", "", "899"),
        arrayOf("Package 4: Thyroid Check", "", "", "", "499"),
        arrayOf("Package 5: Immunity Check", "", "", "", "699")
    )

    val packageDetails = arrayOf(
        "Blood Glucose Fasting\n" +
                "Complete Hemogram\n" +
                "HbA1c\n" +
                "Iron Studies\n" +
                "Kidney Function Test\n" +
                "LDH Lactate Dehydrogenase, Serum\n" +
                "Lipid Profile\n" +
                "Liver Function Test",

        "Blood Glucose Fasting",
        "COVID-19 Antibody IgG",
        "Thyroid Profile-Total (T3, T4 & TSH Ultra-sensitive)",

        "Complete Hemogram\n" +
                "CRP (C Reactive Protein) Quantitative, Serum\n" +
                "Iron Studies\n" +
                "Kidney Function Test\n" +
                "Vitamin D Total-25 Hydroxy\n" +
                "Liver Function Test\n" +
                "Lipid Profile"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab_test)

        Log.d(TAG, "onCreate: LabTest activity created")

        val backbtn:Button = findViewById(R.id.checkOutBMCartButton)
        val gotoCart:Button = findViewById(R.id.backBMCartButton)
        val list = ArrayList<HashMap<String, String>>()
        val lst: ListView = findViewById(R.id.LabTestListView)
        Log.d(TAG, "onCreate: Objects initialized ")
        backbtn.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
        }

        for (i in packages.indices) {
            val item = HashMap<String, String>()
            item["line1"] = packages[i][0]
            item["line2"] = packages[i][1]
            item["line3"] = packages[i][2]
            item["line4"] = packages[i][3]
            item["line5"] = "Cons Fees: " + packages[i][4]
            list.add(item)
        }
        Log.d(TAG, "onCreate: Mapping done")

        val sa: SimpleAdapter = SimpleAdapter(
            this,
            list,
            R.layout.multi_lines,
            arrayOf("line1", "line2", "line3", "line4", "line5"),
            intArrayOf(R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e)
        )

        lst.adapter = sa

        lst.setOnItemClickListener { adapterView, view, i, l ->
            val it = Intent(this, LabTestDetail::class.java)
            it.putExtra("text1", packages[i][0])
            it.putExtra("text2", packageDetails[i])
            it.putExtra("text3", packages[i][4])
            startActivity(it)
        }

        gotoCart.setOnClickListener {
            val intent:Intent = Intent(this,labCart::class.java)
            startActivity(intent)
        }

    }
}
