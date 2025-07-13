package com.ganaljigi.kubf.ui.helper.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.ganaljigi.kubf.ui.helper.component.WebViewTopAppBar
import com.ganaljigi.kubf.ui.helper.component.NoticeWebView
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun SupportScreen(
    onBackClick: () -> Unit
) {
    var tabIndex by remember { mutableStateOf(0) }

    Column (modifier = Modifier.fillMaxSize()) {
        WebViewTopAppBar(
            textTitle = "지원 업무",
            onBackClick = onBackClick
        )

        TabRow(selectedTabIndex = tabIndex) {
            Tab(
                selected = tabIndex == 0,
                onClick = { tabIndex = 0 },
                text = { Text("교수/학습") }
            )
            Tab(
                selected = tabIndex == 1,
                onClick = { tabIndex = 1 },
                text = { Text("기자재") }
            )
            Tab(
                selected = tabIndex == 2,
                onClick = { tabIndex = 2 },
                text = { Text("장학 제도") }
            )
            Tab(
                selected = tabIndex == 3,
                onClick = { tabIndex = 3 },
                text = { Text("시설 현황") }
            )

        }

        val url = when (tabIndex) {
            0 -> ""
            1 -> ""
            2 -> ""
            3 -> ""
            else -> ""
        }

        NoticeWebView(
            url = url,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        )
    }
}