package com.ganaljigi.kubf.ui.helper.component

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun NoticeWebView(
    url: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val webView = remember {
        WebView(context).apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
        }
    }

    AndroidView(
        factory = { webView },
        update = {
            if (it.url!=url && url.isNotBlank()) {
                it.loadUrl(url)
            }
        },
        modifier = modifier
    )
}