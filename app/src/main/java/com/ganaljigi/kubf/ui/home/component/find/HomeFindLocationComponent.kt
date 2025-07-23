package com.ganaljigi.kubf.ui.home.component.find

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ganalijigi.kubf.R
import com.ganaljigi.kubf.ui.theme.Gray2
import com.ganaljigi.kubf.ui.theme.Gray4
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme

@Composable
fun HomeFindLocationComponent(
    modifier: Modifier = Modifier,
    fromLocation: String = "",
    toLocation: String = "",
    onClose: () -> Unit = {},
    onChange: () -> Unit = {},
    onFromLocationClick: () -> Unit = {},
    onToLocationClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .shadow(
                        elevation = 3.dp,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clickable(onClick = onFromLocationClick)
                    .padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_find_item_leading_gray),
                    contentDescription = "From Icon",
                    tint = Color.Unspecified,
                )
                Text(
                    text = fromLocation.ifEmpty { "출발지 입력" },
                    style = KUBFAndroidTheme.typography.medium15.copy(
                        color = if (fromLocation.isEmpty()) Gray2 else Color.Black
                    )
                )

            }
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .shadow(
                        elevation = 3.dp,
                        shape = CircleShape
                    ).background(
                        color = Color.White,
                        shape = CircleShape
                    )
                    .clickable(
                        indication = ripple(false, 20.dp),
                        interactionSource = null,
                        onClick = onClose
                    )
            ) {
                Icon(
                    modifier = Modifier.align(Alignment.Center),
                    painter = painterResource(R.drawable.ic_searchbar_close),
                    contentDescription = "Close Finding",
                    tint = Gray4,
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .shadow(
                        elevation = 3.dp,
                        shape = RoundedCornerShape(10.dp)
                    ).background(
                        color = Color.White,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clickable(onClick = onToLocationClick)
                    .padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_find_item_leading_green),
                    contentDescription = "From Icon",
                    tint = Color.Unspecified,
                )
                Text(
                    text = toLocation.ifEmpty { "도착지 입력" },
                    style = KUBFAndroidTheme.typography.medium15.copy(
                        color = if (toLocation.isEmpty()) Gray2 else Color.Black
                    )
                )

            }
            Box(
                modifier = Modifier
                    .shadow(
                        elevation = 3.dp,
                        shape = CircleShape
                    ).size(40.dp)
                    .background(
                        color = Color.White,
                        shape = CircleShape
                    )
                    .clickable(
                        indication = ripple(false, 20.dp),
                        interactionSource = null,
                        onClick = onChange
                    )
            ) {
                Icon(
                    modifier = Modifier.align(Alignment.Center),
                    painter = painterResource(R.drawable.ic_home_find_change),
                    contentDescription = "Change Location",
                    tint = Gray4,
                )
            }
        }
    }
}


@Preview
@Composable
private fun HomeFindLocationComponentPreview() {
    var startLocation by remember { mutableStateOf("카페 레스티오 경영관") }
    var endLocation by remember { mutableStateOf("") }

    KUBFAndroidTheme {
        HomeFindLocationComponent(
            modifier = Modifier,
            fromLocation = startLocation,
            toLocation = endLocation,
        )
    }
}