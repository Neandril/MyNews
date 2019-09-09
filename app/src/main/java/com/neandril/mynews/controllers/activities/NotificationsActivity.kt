package com.neandril.mynews.controllers.activities

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.neandril.mynews.R
import com.neandril.mynews.utils.MyWorker
import kotlinx.android.synthetic.main.activity_options.*
import java.util.*
import java.util.concurrent.TimeUnit

class NotificationsActivity : AppCompatActivity() {

    /** Preparing consts */
    companion object {
        const val PREFS_FILENAME = "com.neandril.mynews.prefs" // Pref filename
        const val PREFS_TOGGLE = "prefs_toggle" // Bool : position of the switch
        const val PREFS_SECTIONS = "prefs_sections" // sesctions
        const val PREFS_QUERY = "prefs_query" // queryTerm
    }

    /** Variables */
    private lateinit var sectionName : String
    private lateinit var queryTerm : String
    private lateinit var firstCheckboxesLayout : LinearLayout
    private lateinit var secondCheckboxesLayout : LinearLayout

    /** Initialize SharedPreferences */
    private var prefs: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)

        supportActionBar?.title = getString(R.string.notifications)

        prefs = this.getSharedPreferences(PREFS_FILENAME, 0) // Get prefs file

        /** Declaring UI Components */
        firstCheckboxesLayout = findViewById(R.id.first_checkboxes)
        secondCheckboxesLayout = findViewById(R.id.second_checkboxes)
        val searchBtn = findViewById<Button>(R.id.button_search)
        val editTextQueryTerm = findViewById<EditText>(R.id.editText_search_query)
        val dates = findViewById<LinearLayout>(R.id.layout_dates)
        val switch = findViewById<Switch>(R.id.notifications_switch)
        searchBtn.visibility = View.GONE
        dates.visibility = View.GONE

        /** Get the queryTerm */
        queryTerm = editTextQueryTerm.text.toString()

        /** Configure values according to prefs */
        switch.isChecked = prefs?.getBoolean(PREFS_TOGGLE, false) ?: true
        editTextQueryTerm.setText(prefs?.getString(PREFS_QUERY, ""))
        updateCheckboxes()

        /**
         * Manage switch position
         */
        switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if ((editTextQueryTerm.text.toString() == "") || (getCheckedSections() == "")) {
                    Toast.makeText(this, R.string.notificationsFillTheFields, Toast.LENGTH_SHORT).show()
                    switch.isChecked = false
                } else {
                    Toast.makeText(this, R.string.notificationsEnabled, Toast.LENGTH_SHORT).show()
                    /** Apply the switch position */
                    val editor = prefs?.edit()
                    editor
                        ?.putBoolean(PREFS_TOGGLE, true)
                        ?.apply()

                    /** Start the worker */
                    startWorker()
                }
            } else {
                Toast.makeText(this, R.string.notificationsDisabled, Toast.LENGTH_SHORT).show()
                val editor = prefs?.edit()
                editor
                    ?.putBoolean(PREFS_TOGGLE, false)
                    ?.putString(PREFS_QUERY, "")
                    ?.putString(PREFS_SECTIONS, "")
                    ?.apply()
                WorkManager.getInstance().cancelAllWorkByTag("MyTag")
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
                    sectionName += v.text.toString() + " "
                }
            }
        }

        for (i in 0 until secondCheckboxesLayout.childCount) {
            val v = secondCheckboxesLayout.getChildAt(i)
            if (v is CheckBox) {
                if (v.isChecked) {
                    sectionName += v.text.toString() + " "
                }
            }
        }
        return sectionName
    }

    /**
     * Check the boxes according the prefs
     */
    private fun updateCheckboxes() {
        val s = prefs?.getString(PREFS_SECTIONS, "")
        val s1 = s?.split(" ")
        s as CharSequence

        for (i in 0 until firstCheckboxesLayout.childCount) {
            val v = firstCheckboxesLayout.getChildAt(i)
            if (v is CheckBox) {
                if (s1?.contains(v.text) == true) {
                    v.isChecked = true
                }
            }
        }

        for (i in 0 until secondCheckboxesLayout.childCount) {
            val v = secondCheckboxesLayout.getChildAt(i)
            if (v is CheckBox) {
                if (s1?.contains(v.text) == true) {
                    v.isChecked = true
                }
            }
        }
    }

    /**
     * When activity is paused, save datas
     */
    override fun onPause() {
        super.onPause()
        queryTerm = editText_search_query.text.toString()
        if (prefs?.getBoolean(PREFS_TOGGLE, false) == true) {
            val editor = prefs?.edit()
            editor
                ?.putString(PREFS_QUERY, queryTerm)
                ?.putString(PREFS_SECTIONS, getCheckedSections())
                ?.apply()
        }

        startWorker()
    }

    /** Init and run the worker */
    private fun startWorker() {
        // Initialize calendars
        val currentDate = Calendar.getInstance()
        val dueDate = Calendar.getInstance()

        // Set Execution around 12:00:00 AM
        dueDate.set(Calendar.HOUR_OF_DAY, 12)
        dueDate.set(Calendar.MINUTE, 0)
        dueDate.set(Calendar.SECOND, 0)

        // Get the remaining time (in Ms) before next execution
        if (dueDate.before(currentDate)) {
            dueDate.add(Calendar.HOUR_OF_DAY, 24)
        }
        val timeDiff = dueDate.timeInMillis - currentDate.timeInMillis

        // Set constraints : network needed
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        // Build the worker
        val dailyWorkRequest = OneTimeWorkRequestBuilder<MyWorker>()
            .setConstraints(constraints)
            .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS)
            .addTag("MyTag")
            .build()

        WorkManager.getInstance().enqueue(dailyWorkRequest)

        /**
        val worker = PeriodicWorkRequest
            .Builder(MyWorker::class.java, 1, TimeUnit.DAYS)
            .addTag("MyTag")
            .build()

        WorkManager.getInstance().enqueue(worker)
        **/
    }
}
