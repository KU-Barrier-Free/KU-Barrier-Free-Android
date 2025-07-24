package com.ganaljigi.kubf.ui.home.component.find

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
) {
    Log.d("HomeView", "HomeRouteInfo: selectedRoute = $selectedRoute, routeResults = $routeResults")
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        RouteMode.entries.forEach { routeMode ->
            routeResults.find { it.routeMode == routeMode }?.let { route ->
                HomeRouteInfoItem(
                    modifier = Modifier.weight(1f),
                    routeResult = route,
                    isSelected = selectedRoute.routeMode == routeMode,
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
) {
    Column(
        modifier = modifier
            .background(
                if (isSelected) LightGreen else Color.White,
                shape = RoundedCornerShape(20.dp)
            )
            .border(
                width = 1.dp,
                color = if (isSelected) MainGreen else Color.LightGray,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(11.dp)
    ) {
        Text(
            text = routeResult.routeMode.label,
            style = KUBFAndroidTheme.typography.semiBold16,
            color = if (isSelected) MainGreen else Gray4
        )
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = buildAnnotatedString {
                    append(routeResult.time.toString())
                    withStyle(
                        style = KUBFAndroidTheme.typography.regular14.toSpanStyle()
                    ) {
                        append("분")
                    }
                },
                style = KUBFAndroidTheme.typography.semiBold20
            )
            Text(
                text = routeResult.distance.toDistanceString(),
                style = KUBFAndroidTheme.typography.semiBold16,
                color = Gray3
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
            distance = 5000
        ),
        routeResults = listOf(
            RouteResult(routeMode = RouteMode.SHORTEST, time = 7, distance = 428),
            RouteResult(routeMode = RouteMode.BARRIER_FREE, time = 14, distance = 1136),
        )
    )
}