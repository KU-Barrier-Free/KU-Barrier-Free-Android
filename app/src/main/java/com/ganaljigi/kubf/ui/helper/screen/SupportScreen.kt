package com.ganaljigi.kubf.ui.helper.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ganaljigi.kubf.ui.helper.component.WebViewTopAppBar
import com.ganaljigi.kubf.ui.helper.component.NoticeWebView

@Composable
fun SupportScreen(
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            WebViewTopAppBar(
                textTitle = "지원 업무",
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        NoticeWebView(
            url = "https://www.konkuk.ac.kr/csd/15234/subview.do",
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}