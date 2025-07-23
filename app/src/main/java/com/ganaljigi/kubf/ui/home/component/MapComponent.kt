package com.ganaljigi.kubf.ui.home.component

import android.R.attr.text
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ganalijigi.kubf.BuildConfig
import com.ganalijigi.kubf.R
import com.ganaljigi.kubf.ui.common.component.StrokeText
import com.ganaljigi.kubf.ui.home.model.BuildingMarker
import com.ganaljigi.kubf.ui.home.model.DoorMarker
import com.ganaljigi.kubf.ui.home.model.MapToggle
import com.ganaljigi.kubf.ui.home.model.ToggleMarker
import com.ganaljigi.kubf.ui.theme.Gray2
import com.ganaljigi.kubf.ui.theme.Gray4
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme
import com.ganaljigi.kubf.ui.theme.MainGreen
import com.ganaljigi.kubf.ui.util.noRippleClickable
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState

@Composable
fun MapComponent(
    modifier: Modifier = Modifier,
    cameraPosition: CameraPositionState,
    selectedBuildingMarker: BuildingMarker? = null,
    toggleMarkers: List<ToggleMarker> = emptyList(),
    buildingMarkers: List<BuildingMarker> = emptyList(),
    doorMarkers: List<DoorMarker> = emptyList(),
    onBuildingMarkerClick: (BuildingMarker) -> Unit = { },
) {

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPosition,
        properties = MapParam.mapProperties,
        uiSettings = MapParam.mapUiSettings,
        googleMapOptionsFactory = { MapParam.mapOptions }
    ) {
        selectedBuildingMarker?.let { marker ->
            Log.d("MapComponent", "1221Selected Building Marker: ${marker.name}")
            BuildingMarker(
                buildingMarker = marker,
                isSelected = true
            )
        }
        toggleMarkers.forEach { mapMarker ->
            ToggleMarker(toggleMarker = mapMarker)
        }
        buildingMarkers.forEach { mapMarker ->
            Log.d("MapComponent", "Building Marker: ${mapMarker.name}")
            if (mapMarker.id != selectedBuildingMarker?.id) {
                BuildingMarker(
                    buildingMarker = mapMarker,
                    isSelected = false,
                ) {
                    onBuildingMarkerClick(it)
                }
            }
        }
        doorMarkers.forEach { mapMarker ->
            DoorMarker(doorMarker = mapMarker)
        }
    }
}

@Composable
private fun ToggleMarker(
    toggleMarker: ToggleMarker
) {
    MarkerComposable(
        state = MarkerState(
            position = LatLng(
                toggleMarker.latitude,
                toggleMarker.longitude
            )
        )
    ) {
        Icon(
            painter = painterResource(
                when (toggleMarker.mapToggle) {
                    MapToggle.CURB -> R.drawable.ic_curb_marker
                    MapToggle.SLOPE -> R.drawable.ic_slope_marker
                    MapToggle.STAIRS -> R.drawable.ic_stairs_marker
                    MapToggle.SPECIAL_MARK -> R.drawable.ic_special_marker
                }
            ),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .size(24.dp)
                .shadow(1.dp)
        )
    }
}

@Composable
private fun BuildingMarker(
    buildingMarker: BuildingMarker,
    isSelected: Boolean = false,
    onClick: (BuildingMarker) -> Unit = { }
) {
    Log.d("MapComponent12", "BuildingMarker: ${buildingMarker.name}, isSelected: $isSelected")
    MarkerComposable(
        onClick = { onClick(buildingMarker); false },
        state = MarkerState(
            position = LatLng(
                buildingMarker.latitude,
                buildingMarker.longitude
            )
        ),
        keys = arrayOf({ buildingMarker.id })
    ) {
        Column(
            modifier = Modifier.noRippleClickable { onClick(buildingMarker) },
            verticalArrangement = Arrangement.spacedBy(1.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(
                    if (isSelected) R.drawable.ic_building_selected
                    else R.drawable.ic_building
                ),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.shadow(1.dp)
            )

            Text(
                text = buildingMarker.name,
                style = KUBFAndroidTheme.typography.semiBold14.copy(
                    color = if (isSelected) MainGreen else Color(0xFF5A6860),
                ),
            )
//            StrokeText(
//                text = buildingMarker.name,
//                style = KUBFAndroidTheme.typography.semiBold14.copy(
//                    color = Color(0xFF5A6860),
//                ),
//                strokeColor = Color.White,
//                strokeWidth = 1.dp,
//            )
        }
    }
}

@Composable
private fun DoorMarker(
    doorMarker: DoorMarker
) {
    MarkerComposable(
        state = MarkerState(
            position = LatLng(
                doorMarker.latitude,
                doorMarker.longitude
            )
        ),
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(
                    color = if (doorMarker.isWheelChairAccessible) MainGreen else Gray4,
                    shape = CircleShape
                )
        ) {
            Text(
                text = doorMarker.label,
                style = KUBFAndroidTheme.typography.medium14.copy(
                    color = Color.White
                ),
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun MapComponentPreview() {
//    MapComponent()
}


object MapParam {
    val mapProperties = MapProperties(
        isBuildingEnabled = true,
        isIndoorEnabled = false,
        isMyLocationEnabled = false,
        isTrafficEnabled = false,
        // 카메라가 이동할 수 있는 범위
        latLngBoundsForCameraTarget = LatLngBounds(
            LatLng(
                37.53927441241805,
                127.0755595262516,
            ),
            LatLng(
                37.54092357787584,
                127.0851599033603
            )
        ),
        mapStyleOptions = null,
        mapType = MapType.NORMAL,
        maxZoomPreference = 21.0f,
        minZoomPreference = 10.0f,
    )
    val mapUiSettings = MapUiSettings(
        compassEnabled = false,
        indoorLevelPickerEnabled = false,
        mapToolbarEnabled = false,
        myLocationButtonEnabled = false,
        rotationGesturesEnabled = true,
        scrollGesturesEnabled = true,
        scrollGesturesEnabledDuringRotateOrZoom = true,
        tiltGesturesEnabled = true,
        zoomControlsEnabled = false,
        zoomGesturesEnabled = true
    )
    val mapOptions = GoogleMapOptions().apply {
        mapId(BuildConfig.GOOGLE_MAPS_ID)
    }
}