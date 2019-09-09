package com.neandril.mynews.controllers.activities

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.*
import com.neandril.mynews.R
import com.neandril.mynews.utils.paddingZero
import java.util.*

/**
 * Search Activity
 */
class SearchActivity : AppCompatActivity() {

    /** Variables */
    private var mQueryItems : ArrayList<String> = arrayListOf() // Array of query items
    private lateinit var bDate : String
    private lateinit var eDate : String
    private lateinit var sectionName : String
    private lateinit var firstCheckboxesLayout : LinearLayout
    private lateinit var secondCheckboxesLayout : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)

        /** UI Config */
        val separator = findViewById<View>(R.id.separator)
        val searchQuery = findViewById<EditText>(R.id.editText_search_query)
        val notifs = findViewById<LinearLayout>(R.id.layout_notifications)
        val beginDate = findViewById<TextView>(R.id.begin_date)
        val endDate = findViewById<TextView>(R.id.end_date)
        val searchButton = findViewById<Button>(com.neandril.mynews.R.id.button_search)

        /** Calendar config */
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val mm = (month + 1).paddingZero()
        bDate = "$year$mm${day.paddingZero()}" // Default beginDate (set to today)
        eDate = "$year$mm${day.paddingZero()}" // Default endDate (set to today)

        firstCheckboxesLayout = findViewById(R.id.first_checkboxes)
        secondCheckboxesLayout = findViewById(R.id.second_checkboxes)

        /** Hide controls that aren't in common with notification's activity */
        separator.visibility = View.GONE
        notifs.visibility = View.GONE

        /** Title */
        supportActionBar?.title = getString(R.string.search_articles)

        /** Display datePicker for beginDate */
        beginDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener
            { _, year, month, dayOfMonth ->
                val pickedMonth = (month + 1).paddingZero()
                beginDate.text = getString(R.string.datesTexts, dayOfMonth.paddingZero(), pickedMonth, year)
                bDate = "$year$pickedMonth${dayOfMonth.paddingZero()}"

            }, year, month, day)
            datePickerDialog.show()
        }

        /** Display datePicker for endDate */
        endDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener
            { _, year, month, dayOfMonth ->
                val pickedMonth = (month + 1).paddingZero()
                endDate.text = getString(R.string.datesTexts, dayOfMonth.paddingZero(), pickedMonth, year)
                eDate = "$year$pickedMonth${dayOfMonth.paddingZero()}"

            }, year, month, day)
            datePickerDialog.show()
        }

        /** Search Button */
        searchButton.setOnClickListener {
            /** Clearing the query list each time */
            mQueryItems.clear()

            val query = searchQuery.text.toString()
            val intent = Intent(this, ResultsActivity::class.java)

            // Retrieve sections
            getCheckedSections()

            if (query == "") {
                Toast.makeText(applicationContext, R.string.enterKeyword, Toast.LENGTH_LONG).show()
            } else {
                if (isSelectionsValid()) {
                    if (eDate < bDate) {
                        Toast.makeText(applicationContext, R.string.verifyDates, Toast.LENGTH_LONG).show()
                    } else {
                        // Replace spaces by comma for query term
                        mQueryItems.addAll(listOf(query.replace(" ", ","), bDate, eDate, sectionName, "0"))
                        Log.e("Search", "Query : $query")
                        intent.putStringArrayListExtra("query", mQueryItems)
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(applicationContext, R.string.chooseCategory, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    /**
     * Return checked sections
     */
    private fun getCheckedSections() : String {
        sectionName = ""

        for (i in 0 until firstCheckboxesLayout.childCount) {
            val v = firstCheckboxesLayout.getChildAt(i)
            if (v is CheckBox) {
                if (v.isChecked) {
                    sectionName += v.text.toString() + ","
                }
            }
        }

        for (i in 0 until secondCheckboxesLayout.childCount) {
            val v = secondCheckboxesLayout.getChildAt(i)
            if (v is CheckBox) {
                if (v.isChecked) {
                    sectionName += v.text.toString() + ","
                }
            }
        }
        return sectionName
    }

    /**
     * Check if at least one section was picked
     */
    private fun isSelectionsValid() : Boolean {
        if (sectionName == "") {
            return false
        }
        return true
    }
}
