package com.neandril.mynews.controllers.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.neandril.mynews.R
import kotlinx.android.synthetic.main.activity_webview.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class WebviewActivity : AppCompatActivity() {

    var mWebview : WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        val url = intent.getStringExtra("url")
        val title = intent.getStringExtra("title")

        supportActionBar?.title = title

        mWebview = findViewById(R.id.webview)

        mWebview!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url : String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }

        mWebview?.loadUrl(url)
    }
}
