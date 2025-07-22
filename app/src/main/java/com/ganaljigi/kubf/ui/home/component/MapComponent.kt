package com.ganaljigi.kubf.ui.home.component

import androidx.compose.material3.ModalBottomSheetDefaults.properties
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ganalijigi.kubf.BuildConfig
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings

@Composable
fun MapComponent(
    modifier: Modifier = Modifier,
    cameraPosition: CameraPositionState,
    markers: List<Any> = emptyList(),
) {
    val mapProperties = MapProperties(
        isBuildingEnabled = true,
        isIndoorEnabled = false,
        isMyLocationEnabled = true,
        isTrafficEnabled = false,
        // 카메라가 이동할 수 있는 범위
        latLngBoundsForCameraTarget = LatLngBounds(
            LatLng(
                37.53927441241805,
                127.0755595262516,
            ),
            LatLng(
                37.54092357787584,
                127.0801599033603
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
        myLocationButtonEnabled = true,
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
    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPosition,
//        properties = MapParam.mapProperties,
        uiSettings = mapUiSettings,
        googleMapOptionsFactory = { mapOptions }
    ) {
        // TODO : 마커 표시
        // markers.forEach { marker -> ... }
    }
}

@Preview
@Composable
private fun MapComponentPreview() {
//    MapComponent()
}


object MapParam {
    val mapProperties = MapProperties(
        isBuildingEnabled = true,
        isIndoorEnabled = false,
        isMyLocationEnabled = true,
        isTrafficEnabled = false,
        // 카메라가 이동할 수 있는 범위
        latLngBoundsForCameraTarget = LatLngBounds(
            LatLng(
                37.53927441241805,
                127.0801599033603
            ),
            LatLng(
                37.54092357787584,
                127.0755595262516,
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
        myLocationButtonEnabled = true,
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