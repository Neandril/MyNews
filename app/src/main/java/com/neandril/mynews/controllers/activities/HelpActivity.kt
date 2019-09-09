package com.neandril.mynews.controllers.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.neandril.mynews.R
import kotlinx.android.synthetic.main.activity_help.*

/**
 * Activity Help
 */
class HelpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        /** For each strings found in the string array, create a new textview */
        for (s in resources.getStringArray(R.array.help_tips)) {
            val tv = TextView(this)
            tv.text = s
            layout_help_tip.addView(tv)
        }
    }
}
