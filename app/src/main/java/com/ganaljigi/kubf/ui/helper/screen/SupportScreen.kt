package com.ganaljigi.kubf.ui.helper.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.ganaljigi.kubf.ui.helper.component.WebViewTopAppBar
import com.ganaljigi.kubf.ui.helper.component.NoticeWebView
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme
import com.ganaljigi.kubf.ui.theme.MainGreen
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*

@Composable
fun SupportScreen(
    onBackClick: () -> Unit
) {
    var tabIndex by remember { mutableIntStateOf(0) }

    val urls =  listOf (
        "https://www.konkuk.ac.kr/csd/15230/subview.do",
        "https://www.konkuk.ac.kr/csd/15231/subview.do",
        "https://www.konkuk.ac.kr/csd/15232/subview.do",
        "https://www.konkuk.ac.kr/csd/15233/subview.do"
    )

    val  tabs = listOf("교수/학습", "기자재", "장학 제도", "시설 현황")

    Column (modifier = Modifier.fillMaxSize()) {
        WebViewTopAppBar(
            textTitle = "지원 업무",
            onBackClick = onBackClick
        )

        TabRow(
            selectedTabIndex = tabIndex,
            containerColor = Color.White,
            contentColor = MainGreen,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier
                        .tabIndicatorOffset(tabPositions[tabIndex])
                        .height(5.dp),
                    color = MainGreen
                )
            },
            modifier = Modifier.height(40.dp)
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = tabIndex == index,
                    onClick = { tabIndex = index },
                    text = {
                        Text(
                            text = title,
                            style = KUBFAndroidTheme.typography.medium14
                        )
                    }
                )
            }
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
        ) {
            urls.forEachIndexed { index, url ->
                if (tabIndex == index) {
                    NoticeWebView(
                        url = url,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

//TODO: 탭 바꿀때 깜빡이는 현상 없애기