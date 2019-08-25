package com.neandril.mynews.controllers.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import com.neandril.mynews.R

class WebviewActivity : AppCompatActivity() {

    var mWebview : WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        val url = intent.getStringExtra("url")
        val title = intent.getStringExtra("title")

        supportActionBar?.title = title

        mWebview = findViewById(R.id.webview)

        mWebview?.loadUrl(url)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item?.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }


    }
}