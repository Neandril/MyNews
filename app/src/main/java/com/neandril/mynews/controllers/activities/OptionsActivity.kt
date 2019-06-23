package com.neandril.mynews.controllers.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import com.neandril.mynews.R
import kotlinx.android.synthetic.main.activity_options.view.*


class OptionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.neandril.mynews.R.layout.activity_options)

        val separator = findViewById<View>(R.id.separator)
        val notifs = findViewById<LinearLayout>(R.id.layout_notifications)

        separator.visibility = View.GONE
        notifs.visibility = View.GONE

        supportActionBar?.title = getString(R.string.search_articles)
    }
}
