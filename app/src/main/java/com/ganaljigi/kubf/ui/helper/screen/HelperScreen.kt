package com.ganaljigi.kubf.ui.helper.screen

import android.R.attr.onClick
import android.R.attr.text
import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ganaljigi.kubf.ui.helper.component.information.InfoBox
import com.ganaljigi.kubf.ui.helper.component.information.InformationTitle
import com.ganaljigi.kubf.ui.helper.component.notice.NoticeItem
import com.ganaljigi.kubf.ui.helper.component.notice.NoticeTitle
import com.ganaljigi.kubf.ui.helper.component.shortcut.ShortCutItem
import com.ganaljigi.kubf.ui.helper.component.shortcut.ShortCutTitle
import com.ganaljigi.kubf.ui.helper.component.topappbar.HelperTopAppBar

@Composable
fun HelperScreen(
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = { HelperTopAppBar(onBackClick = onBackClick) },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            //공지사항
            NoticeTitle {}
            NoticeItem(
                title = "",
                date = "",
                number = 47,
                index = 0
            )
            NoticeItem(
                title = "",
                date = "",
                number = 46,
                index = 1
            )
            NoticeItem(
                title = "",
                date = "",
                number = 45,
                index = 2
            )

            Spacer(modifier = Modifier.height(16.dp))

            //바로가기
            ShortCutTitle()
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                ShortCutItem(
                    text = "장애학생 도우미",
                    //iconResId =,
                    onClick = {}
                )
                ShortCutItem(
                    text = "지원 업무",
                    //iconResId =,
                    onClick = {}
                )
                ShortCutItem(
                    text = "채용 정보",
                    //iconResId =,
                    onClick = {}
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            //정보
            InformationTitle()
            InfoBox()

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview (showBackground = true)
@Composable
fun HelperScreenPreview() {
    HelperScreen {  }
}