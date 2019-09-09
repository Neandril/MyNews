package com.neandril.mynews.controllers.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView
import com.neandril.mynews.R

/**
 * Webview Activity
 */
class WebviewActivity : AppCompatActivity() {

    private var mWebview : WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        val url = intent.getStringExtra("url")
        val title = intent.getStringExtra("title")

        supportActionBar?.title = title

        mWebview = findViewById(R.id.webview)

        mWebview?.loadUrl(url)
    }

    /**
     * Override the up button
     */
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}