package com.neandril.mynews.controllers.activities

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import com.neandril.mynews.R
import com.neandril.mynews.utils.AlarmReceiver
import kotlinx.android.synthetic.main.activity_options.*
import java.util.*

class NotificationsActivity : AppCompatActivity() {

    /** Preparing consts */
    companion object {
        const val PREFS_FILENAME = "com.neandril.mynews.prefs" // Pref filename
        const val PREFS_TOGGLE = "prefs_toggle" // Bool : position of the switch
        const val PREFS_SECTIONS = "prefs_sections" // sections
        const val PREFS_QUERY = "prefs_query" // queryTerm
    }

    /** Variables */
    private lateinit var sectionName : String
    private lateinit var queryTerm : String

    /** Initialize SharedPreferences */
    private var prefs: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)

        updateUi()

        prefs = this.getSharedPreferences(PREFS_FILENAME, 0) // Get prefs file

        /** Get the queryTerm */
        queryTerm = editText_search_query.text.toString()

        /** Configure values according to prefs */
        notifications_switch.isChecked = prefs?.getBoolean(PREFS_TOGGLE, false) ?: true
        editText_search_query.setText(prefs?.getString(PREFS_QUERY, ""))
        updateCheckboxes()

        /**
         * Manage switch position
         */
        notifications_switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                switchChecked(true)
            } else {
                switchChecked(false)
            }
        }
    }

    /**
     * Switch Management
     */
    private fun switchChecked(value: Boolean) {
        if (value) {
            if ((editText_search_query.text.toString() == "") || (getCheckedSections() == "")) {
                Toast.makeText(this, R.string.notificationsFillTheFields, Toast.LENGTH_SHORT).show()
                notifications_switch.isChecked = false
            } else {
                Toast.makeText(this, R.string.notificationsEnabled, Toast.LENGTH_SHORT).show()
                /** Apply the switch position */
                val editor = prefs?.edit()
                editor
                    ?.putBoolean(PREFS_TOGGLE, true)
                    ?.apply()

                startAlarm()
            }
        } else {
            Toast.makeText(this, R.string.notificationsDisabled, Toast.LENGTH_SHORT).show()

            val editor = prefs?.edit()
            editor
                ?.putBoolean(PREFS_TOGGLE, false)
                ?.putString(PREFS_QUERY, "")
                ?.putString(PREFS_SECTIONS, "")
                ?.apply()
            cancelAlarm()
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
            .forEach { sectionName += it.text.toString() + " " }

        (0 until second_checkboxes.childCount)
            .map { second_checkboxes.getChildAt(it) }
            .filterIsInstance<CheckBox>()
            .filter { it.isChecked }
            .forEach { sectionName += it.text.toString() + " " }

        return sectionName
    }

    /**
     * Check the boxes according the prefs
     */
    private fun updateCheckboxes() {
        val s = prefs?.getString(PREFS_SECTIONS, "")
        val s1 = s?.split(" ")
        s as CharSequence

        (0 until first_checkboxes.childCount)
            .map { first_checkboxes.getChildAt(it) }
            .filterIsInstance<CheckBox>()
            .filter { s1?.contains(it.text) == true }
            .forEach { it.isChecked = true }

        (0 until second_checkboxes.childCount)
            .asSequence()
            .map { second_checkboxes.getChildAt(it) }
            .filterIsInstance<CheckBox>()
            .filter { s1?.contains(it.text) == true }
            .forEach { it.isChecked = true }
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
            startAlarm()
        }
    }

    /**
     * Set up the alarm
     */
    private fun startAlarm() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 12)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.add(Calendar.DATE, 1)

        val alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)

        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    /**
     * Cancel the alarm
     */
    private fun cancelAlarm() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(applicationContext, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(applicationContext, 0, intent, 0)

        alarmManager.cancel(pendingIntent)
    }

    /**
     * Update UI components
     */
    private fun updateUi() {
        button_search.visibility = View.GONE
        layout_dates.visibility = View.GONE

        /** Toolbar title */
        supportActionBar?.title = getString(R.string.notifications)
    }
}
