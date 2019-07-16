package com.neandril.mynews.controllers.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import com.neandril.mynews.R


class NotificationsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)

        supportActionBar?.title = getString(R.string.notifications)

        val searchBtn = findViewById<Button>(R.id.button_search)
        val dates = findViewById<LinearLayout>(R.id.layout_dates)

        searchBtn.visibility = View.GONE
        dates.visibility = View.GONE
    }
}
