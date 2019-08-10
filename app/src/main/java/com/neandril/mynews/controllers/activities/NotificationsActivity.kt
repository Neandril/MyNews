package com.neandril.mynews.controllers.activities

import android.graphics.LinearGradient
import android.opengl.Visibility
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Layout
import android.view.View
import android.widget.*
import com.neandril.mynews.R
import kotlinx.android.synthetic.main.activity_options.*
import kotlinx.android.synthetic.main.activity_options.view.*


class NotificationsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)

        supportActionBar?.title = getString(R.string.notifications)

        val searchBtn = findViewById<Button>(R.id.button_search)
        val dates = findViewById<LinearLayout>(R.id.layout_dates)
        val switch = findViewById<Switch>(R.id.notifications_switch)

        searchBtn.visibility = View.GONE
        dates.visibility = View.GONE

        switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(this, R.string.notificationsEnabled, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, R.string.notificationsDisabled, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
