package com.neandril.mynews.controllers.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.neandril.mynews.R

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        supportActionBar?.title = getString(R.string.about_title)
    }
}
