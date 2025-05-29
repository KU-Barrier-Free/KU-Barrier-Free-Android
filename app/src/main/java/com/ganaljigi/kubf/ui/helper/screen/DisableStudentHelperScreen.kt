package com.ganaljigi.kubf.ui.helper.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ganaljigi.kubf.ui.helper.component.WebViewTopAppBar
import com.ganaljigi.kubf.ui.helper.component.NoticeWebView

@Composable
fun DisableStudentHelperScreen(
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            WebViewTopAppBar(
                textTitle = "장애학생 도우미",
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        NoticeWebView(
            url = "https://www.konkuk.ac.kr/csd/15235/subview.do",
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}