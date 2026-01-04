package com.ganaljigi.kubf.ui.home.component.map

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.painter.Painter
import com.ganaljigi.kubf.ui.home.viewmodel.SpecialMarkerInfo
import com.ganaljigi.kubf.ui.util.noRippleClickable
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState

@Composable
fun MarkerComposableInfoWindow(
    markerState: MarkerState,
    isSelected: Boolean,
    specialMarkerInfo: SpecialMarkerInfo,
    onSpecialMarkerClick: () -> Unit = { },
    onSpecialInfoClick: (List<String>) -> Unit = { },
    painters: List<Painter> = emptyList(),
    content: @Composable () -> Unit,
) {
    val icon = rememberComposeBitmapDescriptor(isSelected) { content() }

    MarkerInfoWindow(
        state = markerState,
        onClick = {
            onSpecialMarkerClick()
            false
        },
        onInfoWindowClick = {
            onSpecialInfoClick(specialMarkerInfo.imageUrls)
        },
        icon = icon,
        infoWindowAnchor = Offset(0.5f, -0.2f)
    ) {
        MapSpecialInfo(
            painters = painters,
            description = specialMarkerInfo.description,
            modifier = Modifier.noRippleClickable {
                onSpecialInfoClick(specialMarkerInfo.imageUrls)
            }
        )
    }
}
