package com.ganaljigi.kubf.ui.home.component.find

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ganaljigi.kubf.ui.common.model.RouteMode
import com.ganaljigi.kubf.ui.home.model.RouteResult
import com.ganaljigi.kubf.ui.theme.Gray3
import com.ganaljigi.kubf.ui.theme.Gray4
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme
import com.ganaljigi.kubf.ui.theme.LightGreen
import com.ganaljigi.kubf.ui.theme.MainGreen
import com.ganaljigi.kubf.ui.util.toDistanceString

@Composable
fun HomeRouteInfo(
    modifier: Modifier = Modifier,
    selectedRoute: RouteResult,
    routeResults: List<RouteResult>,
    onRouteSelected: (RouteResult) -> Unit = {},
) {
    Log.d("HomeView", "HomeRouteInfo: selectedRoute = $selectedRoute, routeResults = $routeResults")
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        RouteMode.entries.forEach { routeMode ->
            routeResults.find { it.routeMode == routeMode }?.let { route ->
                HomeRouteInfoItem(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    routeResult = route,
                    isSelected = selectedRoute.routeMode == routeMode,
                    onClick = onRouteSelected
                )
            }
        }
    }
}

@Composable
fun HomeRouteInfoItem(
    modifier: Modifier = Modifier,
    routeResult: RouteResult,
    isSelected: Boolean,
    onClick: (RouteResult) -> Unit = {},
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(
                if (isSelected) LightGreen else Color.White,
                shape = RoundedCornerShape(20.dp)
            )
            .border(
                width = 1.dp,
                color = if (isSelected) MainGreen else Color.LightGray,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable { onClick(routeResult) }
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            text = routeResult.routeMode.label,
            style = KUBFAndroidTheme.typography.semiBold13,
            color = if (isSelected) MainGreen else Gray4
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = buildAnnotatedString {
                    append(routeResult.time.toString())
                    withStyle(
                        style = KUBFAndroidTheme.typography.regular13.toSpanStyle()
                    ) {
                        append("분")
                    }
                },
                style = KUBFAndroidTheme.typography.semiBold18
            )
            Text(
                text = routeResult.distanceText.toDistanceString(),
                style = KUBFAndroidTheme.typography.semiBold13,
                color = Gray3,
                maxLines = 1,
                onTextLayout = { it -> it.lineCount }
            )
        }
    }
}


@Preview
@Composable
private fun HomeFindRouteComponentPreview() {
    HomeRouteInfo(
        selectedRoute = RouteResult(
            routeMode = RouteMode.BARRIER_FREE,
            time = 30,
            distanceText = "5000m"
        ),
        routeResults = listOf(
            RouteResult(routeMode = RouteMode.SHORTEST, time = 7, distanceText = "428m"),
            RouteResult(routeMode = RouteMode.NO_STAIRS, time = 10, distanceText = "600m"),
            RouteResult(routeMode = RouteMode.BARRIER_FREE, time = 14, distanceText = "1136m"),
        )
    )
}