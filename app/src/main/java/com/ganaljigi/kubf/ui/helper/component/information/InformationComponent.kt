package com.ganaljigi.kubf.ui.helper.component.information

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.snapping.SnapPosition.Center.position
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ganalijigi.kubf.R
import com.ganaljigi.kubf.ui.theme.Gray1
import com.ganaljigi.kubf.ui.theme.Gray4
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme
import com.ganaljigi.kubf.ui.theme.MainGreen
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.*
import com.google.android.gms.maps.CameraUpdateFactory
import kotlinx.coroutines.launch
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MapType

//정보 제목 박스
@Composable
fun InformationTitle() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(52.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "정보",
            style = KUBFAndroidTheme.typography.semiBold18.copy(
                fontSize = 18.sp
            )
        )
    }
}

//정보 하나 박스
@Composable
fun InfoItemBox(
    iconResId: Int,
    label: String,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 12.dp)
    ) {
        Row (
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
                //.height(IntrinsicSize.Min)
        ) {
            //작은 박스1: 아이콘, 정보 종류 제목
            Row(
                modifier = Modifier
                    .weight(80f)
                    .height(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id=iconResId),
                    contentDescription = label,
                    modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.Top)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = label,
                    style = KUBFAndroidTheme.typography.semiBold14.copy(
                        fontSize = 14.sp,
                        color = Gray4
                    ),
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))

            //작은 박스2: 안에 설명들?!
            Column (
                modifier = Modifier
                    .weight(212f)
                    .align(Alignment.CenterVertically)
            ) {
                content()
            }
        }
    }
}

//지도 박스
@Composable
fun MapBox(
    modifier: Modifier= Modifier
) {
    val latLng = LatLng(37.54210, 127.0783)
    val latLngState = MarkerState(position = latLng)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(latLng, 17f)
    }

    Box(
        modifier = modifier
            //.size(width = 304.dp, height = 164.98.dp)
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .aspectRatio(1.842f)
            .clip(RoundedCornerShape(10.dp))
            .border(
                width = 1.dp,
                color = Gray1,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        GoogleMap (
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
        ) {
            Marker(
                state = latLngState,
                title = "장애학생지원센터"
            )
        }
    }
}

//전체 정보 박스
@Composable
fun InfoBox(

) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .wrapContentHeight()
            .clip(RoundedCornerShape(8.dp))
            .background(color = Gray1)
            .border(
                color = Gray1,
                shape = RoundedCornerShape(8.dp),
                width = 1.dp
            )
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        //주소
        InfoItemBox(
            iconResId = R.drawable.ic_helper_address,
            label = "주소"
        ) {
            Column {
                Spacer(modifier = Modifier.height(1.5.dp))
                Text(
                    text = "서울시 광진구 능동로 120 (05029)\n건국대학교 학생회관 1층",
                    style = KUBFAndroidTheme.typography.regular14.copy(
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "장애학생 지원센터: 105호\n장애학생 휴게실: 105-1호",
                    style = KUBFAndroidTheme.typography.regular14.copy(
                        fontSize = 14.sp,
                        color = MainGreen,
                        lineHeight = 20.sp
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        //지도
        MapBox()

        Spacer(modifier = Modifier.height(16.dp))

        //전화번호
        InfoItemBox(
            iconResId = R.drawable.ic_helper_phone,
            label = "전화번호"
        ) {
            Column {
                Spacer(modifier = Modifier.height(1.5.dp))
                Text(
                    text = "02-450-3968",
                    modifier = Modifier.height(20.dp),
                    style = KUBFAndroidTheme.typography.regular14.copy(
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        //이메일
        InfoItemBox(
            iconResId = R.drawable.ic_helper_email,
            label = "이메일"
        ) {
            Text(
                text = "csd@konkuk.ac.kr",
                style = KUBFAndroidTheme.typography.regular14.copy(
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun InformationTitlePreview() {
    InformationTitle()
}

@Preview(showBackground = true)
@Composable
fun MapBoxPreview() {
    MapBox()
}

@Preview(showBackground = true)
@Composable
fun InfoItemBoxPreview() {
    InfoItemBox(
        iconResId = R.drawable.ic_helper_phone,
        label = "전화번호"
    ) {
        Text(
            text = "02-450-3968",
            style = KUBFAndroidTheme.typography.regular14.copy(
                fontSize = 14.sp
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InfoBoxPreview() {
    InfoBox()
}