package com.ganaljigi.kubf.ui.helper.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ganaljigi.kubf.ui.helper.component.NoticeWebView
import com.ganaljigi.kubf.ui.helper.component.WebViewTopAppBar


@Composable
fun JobInformationScreen(
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            WebViewTopAppBar (
                textTitle = "채용 정보",
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        NoticeWebView(
            url = "https://www.konkuk.ac.kr/csd/15240/subview.do",
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}