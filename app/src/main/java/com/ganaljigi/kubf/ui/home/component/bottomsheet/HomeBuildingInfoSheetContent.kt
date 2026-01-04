package com.ganaljigi.kubf.ui.home.component.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter.State.Empty.painter
import com.ganalijigi.kubf.R
import com.ganaljigi.kubf.ui.common.component.ConvenienceChip
import com.ganaljigi.kubf.ui.common.component.DoorComponent
import com.ganaljigi.kubf.ui.common.model.Convenience
import com.ganaljigi.kubf.ui.common.model.DoorInfo
import com.ganaljigi.kubf.ui.home.viewmodel.HomeBuildingInfo
import com.ganaljigi.kubf.ui.theme.Gray2
import com.ganaljigi.kubf.ui.theme.Gray3
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme
import com.ganaljigi.kubf.ui.util.noRippleClickable
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun HomeBuildingInfoSheetContent(
    modifier: Modifier = Modifier,
    buildingInfo: HomeBuildingInfo,
    onItemClick: (Long) -> Unit = {},
) {
    val buildingNumber = if(buildingInfo.buildingNumber == 0) "없음" else buildingInfo.buildingNumber.toString()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(100.dp)
                .height(28.dp)
                .padding(top = 8.dp, bottom = 16.dp)
                .background(
                    color = Gray2,
                    shape = RoundedCornerShape(4.dp)
                )
        )
        Column(
            modifier = modifier
                .fillMaxWidth()
                .noRippleClickable { onItemClick(buildingInfo.id) }
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Text(
                        text = buildingInfo.name,
                        style = KUBFAndroidTheme.typography.bold18
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = "건물번호: $buildingNumber",
                        style = KUBFAndroidTheme.typography.regular14.copy(
                            color = Gray3
                        )
                    )
                }
                Icon(
                    painter = painterResource(R.drawable.ic_chevron_right),
                    contentDescription = null,
                    tint = Color.Unspecified,
                )
            }
            Spacer(Modifier.height(12.dp))
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                buildingInfo.convenienceList.forEach { convenience ->
                    ConvenienceChip(convenience = convenience)
                }
            }
            Spacer(Modifier.height(20.dp))
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "출입문",
                style = KUBFAndroidTheme.typography.semiBold18
            )
            Spacer(Modifier.height(12.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
            ) {
                items(buildingInfo.doorInfoList.size) { index ->
                    DoorComponent(
                        modifier = Modifier
                            .width(80.dp),
                        doorInfo = buildingInfo.doorInfoList[index]
                    )
                }
            }
        }
        Spacer(Modifier.height(13.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun HomeBuildingInfoBottomSheetPreview() {
    HomeBuildingInfoSheetContent(
        modifier = Modifier.fillMaxWidth(),
        buildingInfo = HomeBuildingInfo(
            id = 1L,
            name = "경영관",
            buildingNumber = 123,
            latitude = 37.5665,
            longitude = 126.978,
            convenienceList = persistentListOf(
                Convenience.CONVENIENCE,
                Convenience.CAFE,
            ),
            doorInfoList = persistentListOf(
                DoorInfo(
                    label = "B",
                    imageUrl = "",
                    description = "입구 설명",
                    isWheelchairAccessible = true
                ),
                DoorInfo(
                    label = "B",
                    imageUrl = "",
                    description = "입구 설명",
                    isWheelchairAccessible = true
                ),
                DoorInfo(
                    label = "B",
                    imageUrl = "",
                    description = "입구 설명",
                    isWheelchairAccessible = false
                ),
                DoorInfo(
                    label = "B",
                    imageUrl = "",
                    description = "입구 설명",
                    isWheelchairAccessible = false
                ),
                DoorInfo(
                    label = "B",
                    imageUrl = "",
                    description = "입구 설명",
                    isWheelchairAccessible = false
                ),
            )
        ),
        onItemClick = {}
    )
}