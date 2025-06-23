package com.ganaljigi.kubf.ui.helper.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ganaljigi.kubf.ui.helper.component.WebViewTopAppBar
import com.ganaljigi.kubf.ui.helper.component.NoticeWebView

@Composable
fun HelperNoticeScreen(
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            WebViewTopAppBar(
                textTitle = "공지사항",
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        NoticeWebView(
            url = "https://www.konkuk.ac.kr/csd/15238/subview.do?enc=Zm5jdDF8QEB8JTJGYmJzJTJGY3NkJTJGNTM1JTJGYXJ0Y2xMaXN0LmRvJTNG",
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun HelperNoticeScreenPreview() {
//    HelperNoticeScreen (
//        onBackClick = {}
//    )
//}