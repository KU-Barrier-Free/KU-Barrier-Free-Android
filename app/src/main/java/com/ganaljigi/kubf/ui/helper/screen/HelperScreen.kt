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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ganalijigi.kubf.R
import com.ganaljigi.kubf.ui.helper.component.information.InfoBox
import com.ganaljigi.kubf.ui.helper.component.information.InformationTitle
import com.ganaljigi.kubf.ui.helper.component.notice.NoticeItem
import com.ganaljigi.kubf.ui.helper.component.notice.NoticeTitle
import com.ganaljigi.kubf.ui.helper.component.shortcut.ShortCutItem
import com.ganaljigi.kubf.ui.helper.component.shortcut.ShortCutTitle
import com.ganaljigi.kubf.ui.helper.component.topappbar.HelperTopAppBar
import com.ganaljigi.kubf.ui.helper.viewmodel.HelperViewModel
import com.ganaljigi.kubf.ui.helper.viewmodel.Notice

@Composable
fun HelperScreen(
    onBackClick: () -> Unit,
    viewModel: HelperViewModel = viewModel(),
    onNoticeClick: (String) -> Unit,
    //onNoticeAllClick: () -> Unit
) {
    val notices = viewModel.notices.value

    Scaffold(
        topBar = { HelperTopAppBar(onBackClick = onBackClick) },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
        ) {
            //공지사항
            NoticeTitle {/*onClick = onNoticeAllClick*/}
            notices.take(3).forEachIndexed { index, notice: Notice ->
                NoticeItem(
                    title = notice.title,
                    date = notice.date,
                    number = notices.size - index,
                    index = index,
                    onClick = { onNoticeClick(notice.url) }
                )
            }
//            NoticeItem(
//                title = "[KIRD] 포용성장사업_이공계 장애 대학(원)생 경력개발 멘토링 모집 홍보 새글",
//                date = "2025.05.13",
//                number = 47,
//                index = 0
//            )
//            NoticeItem(
//                title = "스텝업탐방캠프 2기 참여자 모집",
//                date = "2025.05.13",
//                number = 46,
//                index = 1
//            )
//            NoticeItem(
//                title = "2025 동행, 국가유산 ‘빛나는 우리를 만나다’「마음으로 듣는 국가유산」 역사 기행 참여 안내 새글",
//                date = "2025.05.13",
//                number = 45,
//                index = 2
//            )

            Spacer(modifier = Modifier.height(16.dp))

            //바로가기
            ShortCutTitle()
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
            ) {
                ShortCutItem(
                    text = "장애학생 도우미",
                    iconResId = R.drawable.ic_helper_disablestudenthelper,
                    onClick = {}
                )
                ShortCutItem(
                    text = "지원 업무",
                    iconResId = R.drawable.ic_helper_support,
                    onClick = {}
                )
                ShortCutItem(
                    text = "채용 정보",
                    iconResId = R.drawable.ic_helper_jobinformation,
                    onClick = {}
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            //정보
            InformationTitle()
            InfoBox(
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview (showBackground = true)
@Composable
fun HelperScreenPreview() {
    HelperScreen(onBackClick = {}, onNoticeClick = {})
}