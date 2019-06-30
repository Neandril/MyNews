package com.neandril.mynews.controllers.activities

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.neandril.mynews.R
import java.util.*


class OptionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)

        val separator = findViewById<View>(R.id.separator)
        val notifs = findViewById<LinearLayout>(R.id.layout_notifications)
        val beginDate = findViewById<TextView>(R.id.begin_date)
        val endDate = findViewById<TextView>(R.id.end_date)

        separator.visibility = View.GONE
        notifs.visibility = View.GONE

        supportActionBar?.title = getString(R.string.search_articles)

        beginDate.setOnClickListener {
            Toast.makeText(this, "Clicked", Toast.LENGTH_LONG).show()

            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            Log.e("Options", " " + day + "/" + (month +1) + "/" + year)
            val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener
            { _, year, monthOfYear, dayOfMonth ->
                val mm = (monthOfYear +1)
                beginDate.text = (" " + (if (dayOfMonth.toString().length == 1) "0$dayOfMonth" else (dayOfMonth)) + "/" + (if (mm.toString().length == 1) "0$mm" else (mm)) + "/" + year)
            }, year, month, day)

            datePickerDialog.show()
        }

        endDate.setOnClickListener {
            Toast.makeText(this, "Clicked", Toast.LENGTH_LONG).show()

            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            Log.e("Options", " " + day + "/" + (month +1) + "/" + year)
            val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener
            { _, year, monthOfYear, dayOfMonth ->
                val mm = (monthOfYear +1)
                endDate.text = (" " + (if (dayOfMonth.toString().length == 1) "0$dayOfMonth" else (dayOfMonth)) + "/" + (if (mm.toString().length == 1) "0$mm" else (mm)) + "/" + year)
            }, year, month, day)

            datePickerDialog.show()
        }

    }
}
