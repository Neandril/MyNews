package com.neandril.mynews.controllers.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import com.neandril.mynews.R
import kotlinx.android.synthetic.main.activity_options.view.*


class OptionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.neandril.mynews.R.layout.activity_options)

        val EditText = findViewById<EditText>(R.id.beginDate)

        supportActionBar?.title = "Search Articles"
    }
}
