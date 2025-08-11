package com.ganaljigi.kubf.ui.roominfo

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ganaljigi.kubf.ui.theme.Gray1
import com.ganaljigi.kubf.ui.theme.Gray3
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme
import com.ganaljigi.kubf.ui.theme.MainGreen
import java.nio.file.WatchEvent

@Composable
fun DeskAndChairComponent(
    allInOne: Boolean,
    cinemaSeat: Boolean,
    oneSeat: Boolean,
    twoSeat: Boolean,
    multiSeat: Boolean,
    panel: Boolean,
    backOfChair: Boolean,
    wheelChair: Boolean,
    wheelchairTable: Boolean,
    computerTable: Boolean,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(start = 20.dp)) {
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            color = Gray1,
            thickness = 1.dp
        )

        Text(
            text = "책상 · 의자 종류",
            style = KUBFAndroidTheme.typography.semiBold16
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 위쪽 그룹 //TODO: form 또는 LazyColumn으로 고치면 좋음
        DeskAndChairItem("일체형", allInOne)
        Spacer(modifier = Modifier.height(12.dp))
        DeskAndChairItem("영화관", cinemaSeat)
        Spacer(modifier = Modifier.height(12.dp))
        DeskAndChairItem("1인용", oneSeat)
        Spacer(modifier = Modifier.height(12.dp))
        DeskAndChairItem("2인용", twoSeat)
        Spacer(modifier = Modifier.height(12.dp))
        DeskAndChairItem("다인용", multiSeat)

        Spacer(modifier = Modifier.height(12.dp))

        // 중간 그룹
        DeskAndChairItem("다리 가리개", panel)
        Spacer(modifier = Modifier.height(12.dp))
        DeskAndChairItem("등받이", backOfChair)
        Spacer(modifier = Modifier.height(12.dp))
        DeskAndChairItem("바퀴 의자", wheelChair)

        Spacer(modifier = Modifier.height(12.dp))

        // 아래 그룹
        DeskAndChairItem("휠체어용 책상", wheelchairTable)
        Spacer(modifier = Modifier.height(12.dp))
        DeskAndChairItem("컴퓨터 책상", computerTable)
    }
}

@Composable
fun DeskAndChairItem(
    label: String,
    exists: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = if (exists)
                KUBFAndroidTheme.typography.semiBold16.copy(color = MainGreen)
            else
                KUBFAndroidTheme.typography.regular14.copy(color = Gray3)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = if (exists) "O" else "X",
            style = if (exists)
                KUBFAndroidTheme.typography.semiBold16.copy(color = MainGreen)
            else
                KUBFAndroidTheme.typography.regular14.copy(color = Gray3)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DeskAndChairComponentPreview() {
    KUBFAndroidTheme {
        DeskAndChairComponent(
            allInOne = false,
            cinemaSeat = false,
            oneSeat = true,
            twoSeat = false,
            multiSeat = false,
            panel = true,
            backOfChair = true,
            wheelChair = true,
            wheelchairTable = false,
            computerTable = false
        )
    }
}
