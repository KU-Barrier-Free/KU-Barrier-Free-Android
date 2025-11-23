package com.ganaljigi.kubf.ui.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ganalijigi.kubf.R
import com.ganaljigi.kubf.ui.common.model.DoorInfo
import com.ganaljigi.kubf.ui.theme.Gray3
import com.ganaljigi.kubf.ui.theme.Gray4
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme
import com.ganaljigi.kubf.ui.theme.MainGreen

@Composable
fun DoorComponent(
    modifier: Modifier = Modifier,
    doorInfo: DoorInfo,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Box(
            modifier = Modifier.size(80.dp)
        ) {
            AsyncImage(
                contentScale = ContentScale.Crop,
                model = doorInfo.imageUrl,
                contentDescription = doorInfo.description,
                modifier = Modifier
                    .matchParentSize()
                    .clip(RoundedCornerShape(10.dp)),
            )
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .widthIn(min = 16.dp)
                    .align(Alignment.TopStart)
                    .background(
                        color = Gray4,
                        shape = CircleShape
                    )
            ) {
                Text(
                    text = doorInfo.label,
                    modifier = Modifier.align(Alignment.Center),
                    style = KUBFAndroidTheme.typography.medium14,
                    color = if (doorInfo.isWheelchairAccessible) MainGreen else Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        Text(
            text = "휠체어 진입",
            style = KUBFAndroidTheme.typography.regular13.copy(
                color = Gray4
            ),
            modifier = Modifier.padding(horizontal = 7.dp)
        )
        if (doorInfo.isWheelchairAccessible) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 7.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "가능",
                    style = KUBFAndroidTheme.typography.semiBold14.copy(
                        color = MainGreen
                    ),
                )
                Icon(
                    painter = painterResource(R.drawable.ic_circle_green),
                    contentDescription = null,
                    tint = Color.Unspecified,
                )
            }
        } else {
            Row(
                modifier = Modifier
                    .padding(horizontal = 7.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "불가능",
                    style = KUBFAndroidTheme.typography.semiBold14.copy(
                        color = Gray3
                    ),
                )
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(R.drawable.ic_searchbar_close),
                    contentDescription = null,
                    tint = Gray3,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DoorComponentPreview() {
    DoorComponent(
        doorInfo = DoorInfo(
            id = 1L,
            imageUrl = "https://cdn.pixabay.com/photo/2016/11/21/14/24/door-1845682_1280.png",
            description = "Sample Door",
            label = "A"
        )
    )
}