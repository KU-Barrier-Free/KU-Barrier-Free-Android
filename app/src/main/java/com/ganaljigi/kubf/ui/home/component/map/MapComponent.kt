package com.ganaljigi.kubf.ui.home.component.map

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.allowHardware
import com.ganalijigi.kubf.BuildConfig
import com.ganalijigi.kubf.R
import com.ganaljigi.kubf.ui.home.model.BuildingMarker
import com.ganaljigi.kubf.ui.home.model.DoorMarker
import com.ganaljigi.kubf.ui.home.model.MapToggle
import com.ganaljigi.kubf.ui.home.model.RouteResult
import com.ganaljigi.kubf.ui.home.model.ToggleMarker
import com.ganaljigi.kubf.ui.home.viewmodel.SpecialMarkerInfo
import com.ganaljigi.kubf.ui.theme.Gray4
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme
import com.ganaljigi.kubf.ui.theme.MainGreen
import com.ganaljigi.kubf.ui.util.noRippleClickable
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline

@Composable
fun MapComponent(
    modifier: Modifier = Modifier,
    cameraPosition: CameraPositionState,
    selectedBuildingMarker: BuildingMarker? = null,
    toggleMarkers: List<ToggleMarker> = emptyList(),
    buildingMarkers: List<BuildingMarker> = emptyList(),
    doorMarkers: List<DoorMarker> = emptyList(),
    selectedRouteResult: RouteResult? = null,
    onBuildingMarkerClick: (BuildingMarker) -> Unit = { },
    onSpecialMarkerClick: (ToggleMarker) -> Unit = { },
    onSpecialInfoClick: (List<String>) -> Unit = { },
    selectedSpecialMarker: ToggleMarker? = null,
    specialMarkerInfo: SpecialMarkerInfo?,
    setDefaultMode: () -> Unit = { },
) {
    GoogleMap(
        modifier = modifier,
        onMapClick = { setDefaultMode() },
        cameraPositionState = cameraPosition,
        properties = MapParam.mapProperties,
        uiSettings = MapParam.mapUiSettings,
        googleMapOptionsFactory = { MapParam.mapOptions }
    ) {
        // 선택된 경로만 렌더링
        selectedRouteResult?.let { selectedRoute ->
            if (selectedRoute.pathPoints.isNotEmpty()) {
                Polyline(
                    points = selectedRoute.pathPoints,
                    color = MainGreen,
                    width = 10f,
                    zIndex = 2f
                )
            }
        }
        
        toggleMarkers.forEach { mapMarker ->
            if (mapMarker.mapToggle == MapToggle.SPECIAL_MARK) {
                ToggleSpecialMarker(
                    toggleMarker = mapMarker,
                    toggleIconRes = R.drawable.ic_special_marker,
                    onClick = { onSpecialMarkerClick(mapMarker) },
                    onSpecialInfoClick = onSpecialInfoClick,
                    selectedSpecialMarker = selectedSpecialMarker,
                    specialMarkerInfo = specialMarkerInfo,
                )
            } else {
                ToggleMarker(
                    toggleMarker = mapMarker,
                    toggleIconRes = when (mapMarker.mapToggle) {
                        MapToggle.CURB -> R.drawable.ic_curb_marker
                        MapToggle.SLOPE -> R.drawable.ic_slope_marker
                        MapToggle.STAIRS -> R.drawable.ic_stairs_marker
                        else -> R.drawable.ic_special_marker
                    },
                )
            }
        }
        selectedBuildingMarker?.let { marker ->
            BuildingMarker(
                buildingMarker = marker,
                isSelected = true
            )
        }

        buildingMarkers.forEach { mapMarker ->
            BuildingMarker(
                buildingMarker = mapMarker,
                isSelected = false,
            ) {
                onBuildingMarkerClick(it)
            }
        }
//        doorMarkers.forEach { mapMarker ->
//            DoorMarker(doorMarker = mapMarker)
//        }
    }
}


@Composable
private fun ToggleMarker(
    toggleMarker: ToggleMarker,
    @DrawableRes toggleIconRes: Int,
) {
    MarkerComposable(
        state = MarkerState(
            position = LatLng(
                toggleMarker.latitude,
                toggleMarker.longitude
            )
        ),
    ) {
        Icon(
            painter = painterResource(toggleIconRes),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .size(16.dp)
                .shadow(1.dp)
        )
    }
}

// https://velog.io/@gudrmsglgl/Compose-Google-Map
@Composable
private fun ToggleSpecialMarker(
    toggleMarker: ToggleMarker,
    @DrawableRes toggleIconRes: Int,
    onClick: () -> Unit = { },
    onSpecialInfoClick: (List<String>) -> Unit = { },
    selectedSpecialMarker: ToggleMarker?,
    specialMarkerInfo: SpecialMarkerInfo? = null,
) {
    val recomposeMarker = remember { mutableStateListOf(false, false) }
    val painters = specialMarkerInfo?.let { info ->
        info.imageUrls.take(2).mapIndexed { index, it ->
            rememberAsyncImagePainter(
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(it)
                    .allowHardware(false)
                    .build(),
                placeholder = painterResource(R.drawable.img_special_info),
                error = painterResource(R.drawable.img_special_info),
                onSuccess = { result ->
                    Log.d("MapComponent", "Image loaded successfully: ${result.result}")
                    recomposeMarker[index] = true
                },
            )
        }
    } ?: emptyList()

    val painter1 = if (painters.isNotEmpty()) painters[0] else null
    val painter2 = if (painters.size == 2) painters[1] else null


    key(recomposeMarker[0], recomposeMarker[1]) {
        MarkerComposable(
            state = MarkerState(
                position = LatLng(
                    toggleMarker.latitude,
                    toggleMarker.longitude
                )
            ),
            onClick = {
                if (toggleMarker == selectedSpecialMarker && specialMarkerInfo != null) {
                    onSpecialInfoClick(specialMarkerInfo.imageUrls)
                } else {
                    onClick()
                }
                false
            },
            keys = arrayOf(
                { painter1?.state },
                { painter2?.state },
                { selectedSpecialMarker },
                { specialMarkerInfo })
        ) {
            if (toggleMarker == selectedSpecialMarker && specialMarkerInfo != null) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    MapSpecialInfo(
                        painters = painters,
                        description = specialMarkerInfo.description,
                        modifier = Modifier
                            .noRippleClickable {
                                Log.d(
                                    "MapComponent",
                                    "Special marker clicked: ${specialMarkerInfo.description}"
                                )
                                onSpecialInfoClick(specialMarkerInfo.imageUrls)
                            }
                    )
                    Box(
                        modifier = Modifier
                            .width(2.dp)
                            .height(16.dp)
                            .background(Color.White)
                    )
                    Icon(
                        painter = painterResource(R.drawable.ic_special_marker_selected),
                        contentDescription = null,
                        tint = Color.Unspecified,
                    )
                }
            } else {
                Icon(
                    painter = painterResource(toggleIconRes),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(24.dp)
                        .shadow(1.dp)
                )
            }
        }
    }
}

@Composable
private fun BuildingMarker(
    buildingMarker: BuildingMarker,
    isSelected: Boolean = false,
    onClick: (BuildingMarker) -> Unit = { }
) {
    MarkerComposable(
        onClick = { onClick(buildingMarker); false },
        state = MarkerState(
            position = LatLng(
                buildingMarker.latitude,
                buildingMarker.longitude
            )
        ),
        zIndex = 0f,
        keys = arrayOf({ buildingMarker.id }, { isSelected })
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
        keys = arrayOf({ doorMarker.id }),
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
                37.53727441241805,
                127.0655595262516,
            ),
            LatLng(
                37.54392357787584,
                127.0951599033603
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