package com.aumarbello.showcase.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aumarbello.showcase.databinding.ActivityWebviewBinding

class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url = intent.getStringExtra(URL_EXTRA) ?: return
        binding.webview.settings.apply {
            javaScriptCanOpenWindowsAutomatically = true
            domStorageEnabled = true
        }
        binding.webview.loadUrl(url)
    }

    companion object {
        private const val URL_EXTRA = "webview_url"

        fun getStartIntent(context: Context, url: String): Intent {
            return Intent(context, WebViewActivity::class.java)
                .putExtra(URL_EXTRA, url)
        }
    }
}