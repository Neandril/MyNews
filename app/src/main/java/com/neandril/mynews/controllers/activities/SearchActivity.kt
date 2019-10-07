package com.neandril.mynews.controllers.activities

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import com.neandril.mynews.R
import com.neandril.mynews.utils.paddingZero
import kotlinx.android.synthetic.main.activity_options.*
import java.util.*

/**
 * Search Activity
 */
class SearchActivity : AppCompatActivity() {

    /** Calendar config */
    private val calendar = Calendar.getInstance()
    private val year = calendar.get(Calendar.YEAR)
    private val month = calendar.get(Calendar.MONTH)
    private val day = calendar.get(Calendar.DAY_OF_MONTH)
    private val mm = (month + 1).paddingZero() // Because months are zero-indexed

    /** Dates initializations */
    private var bDate = "$year$mm${day.paddingZero()}" // Default beginDate (set to today)
    private var eDate = "$year$mm${day.paddingZero()}" // Default endDate (set to today)

    /** Variables */
    private var mQueryItems : ArrayList<String> = arrayListOf() // Array of query items
    private lateinit var sectionName : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)

        updateUi()
        datePickersListeners()
        executeSearchRequest()
    }

    private fun datePickersListeners() {
        /** Display datePicker for beginDate */
        begin_date.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener
            { _, year, month, dayOfMonth ->
                val pickedMonth = (month + 1).paddingZero()
                begin_date.text = getString(R.string.datesTexts, dayOfMonth.paddingZero(), pickedMonth, year)
                bDate = "$year$pickedMonth${dayOfMonth.paddingZero()}"

            }, year, month, day)
            datePickerDialog.show()
        }

        /** Display datePicker for endDate */
        end_date.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener
            { _, year, month, dayOfMonth ->
                val pickedMonth = (month + 1).paddingZero()
                end_date.text = getString(R.string.datesTexts, dayOfMonth.paddingZero(), pickedMonth, year)
                eDate = "$year$pickedMonth${dayOfMonth.paddingZero()}"

            }, year, month, day)
            datePickerDialog.show()
        }
    }

    private fun executeSearchRequest() {
        /** Search Button */
        button_search.setOnClickListener {
            /** Clearing the query list each time */
            mQueryItems.clear()

            val query = editText_search_query.text.toString()
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

        (0 until first_checkboxes.childCount)
            .map { first_checkboxes.getChildAt(it) }
            .filterIsInstance<CheckBox>()
            .filter { it.isChecked }
            .forEach { sectionName += it.text.toString() + "," }

        (0 until second_checkboxes.childCount)
            .map { second_checkboxes.getChildAt(it) }
            .filterIsInstance<CheckBox>()
            .filter { it.isChecked }
            .forEach { sectionName += it.text.toString() + "," }
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

    private fun updateUi() {
        /** Hide controls that aren't in common with notification's activity */
        separator.visibility = View.GONE
        layout_notifications.visibility = View.GONE

        /** Title */
        supportActionBar?.title = getString(R.string.search_articles)
    }
}
