package com.neandril.mynews.controllers.activities

import android.graphics.LinearGradient
import android.opengl.Visibility
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Layout
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
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

        searchBtn.visibility = View.GONE
        dates.visibility = View.GONE
    }
}
