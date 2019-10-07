package com.neandril.mynews.controllers.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView
import com.neandril.mynews.R
import kotlinx.android.synthetic.main.activity_webview.*

/**
 * Webview Activity
 */
class WebviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        val url = intent.getStringExtra("url")
        val title = intent.getStringExtra("title")

        supportActionBar?.title = title

        webview.loadUrl(url)
    }

    /**
     * Override the up button
     */
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}