package com.ganaljigi.kubf.ui.home.component.map

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.ganaljigi.kubf.ui.home.viewmodel.ToggleUiState
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
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

// TODO: 현재 위치 설정, 줌인/줌아웃 버튼 추가
@Composable
fun MapComponent(
    modifier: Modifier = Modifier,
    cameraPosition: CameraPositionState,
    isLocationPermissionGranted: Boolean = false,
    selectedBuildingMarker: BuildingMarker? = null,
    selectedToggles: ImmutableList<ToggleUiState> = persistentListOf(),
    buildingMarkers: List<BuildingMarker> = emptyList(),
    doorMarkers: List<DoorMarker> = emptyList(),
    curbMarkers: ImmutableList<ToggleMarker> = persistentListOf(),
    slopeMarkers: ImmutableList<ToggleMarker> = persistentListOf(),
    stairsMarkers: ImmutableList<ToggleMarker> = persistentListOf(),
    specialMarkers: ImmutableList<ToggleMarker> = persistentListOf(),
    selectedRouteResult: RouteResult? = null,
    onBuildingMarkerClick: (BuildingMarker) -> Unit = { },
    onSpecialMarkerClick: (ToggleMarker) -> Unit = { },
    onSpecialInfoClick: (List<String>) -> Unit = { },
    selectedSpecialMarker: ToggleMarker? = null,
    specialMarkerInfo: SpecialMarkerInfo?,
    setDefaultMode: () -> Unit = { },
    userLocation: LatLng? = null,
) {
    val currentZoom = cameraPosition.position.zoom
    val markerScale = calculateMarkerScale(
        currentZoom = currentZoom,
        minZoom = MapParam.MIN_ZOOM,
        maxZoom = MapParam.MAX_ZOOM
    )

    GoogleMap(
        modifier = modifier,
        onMapClick = { setDefaultMode() },
        cameraPositionState = cameraPosition,
        properties = MapParam.mapProperties.copy(isMyLocationEnabled = false),
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
        selectedToggles.forEach { toggleUiState ->
            when (toggleUiState.toggle) {
                MapToggle.CURB -> curbMarkers.forEach { mapMarker ->
                    ToggleMarker(
                        toggleMarker = mapMarker,
                        toggleIconRes = R.drawable.ic_curb_marker,
                        scale = markerScale
                    )
                }

                MapToggle.SLOPE -> slopeMarkers.forEach { mapMarker ->
                    ToggleMarker(
                        toggleMarker = mapMarker,
                        toggleIconRes = R.drawable.ic_slope_marker,
                        scale = markerScale
                    )
                }

                MapToggle.STAIRS -> stairsMarkers.forEach { mapMarker ->
                    ToggleMarker(
                        toggleMarker = mapMarker,
                        toggleIconRes = R.drawable.ic_stairs_marker,
                        scale = markerScale
                    )
                }

                MapToggle.SPECIAL_MARK -> specialMarkers.forEach { mapMarker ->
                    val markerState = rememberMarkerState(
                        position = LatLng(mapMarker.latitude, mapMarker.longitude)
                    )
                    if (mapMarker == selectedSpecialMarker && specialMarkerInfo != null) {
                        SelectedSpecialMarker(
                            markerState = markerState,
                            toggleMarker = mapMarker,
                            specialMarkerInfo = specialMarkerInfo,
                            onSpecialInfoClick = onSpecialInfoClick,
                            scale = markerScale
                        )
                    } else {
                        ToggleSpecialMarker(
                            markerState = markerState,
                            toggleMarker = mapMarker,
                            toggleIconRes = R.drawable.ic_special_marker,
                            onClick = { onSpecialMarkerClick(mapMarker) },
                            scale = markerScale
                        )
                    }
                }
            }
        }

        selectedBuildingMarker?.let { marker ->
            BuildingMarker(
                buildingMarker = marker,
                isSelected = true,
                scale = markerScale
            )
        }

        buildingMarkers.forEach { mapMarker ->
            BuildingMarker(
                buildingMarker = mapMarker,
                isSelected = false,
                scale = markerScale
            ) {
                onBuildingMarkerClick(it)
            }
        }
//        doorMarkers.forEach { mapMarker ->
//            DoorMarker(doorMarker = mapMarker)
//        }

        // 사용자 위치 마커 렌더링
        if (isLocationPermissionGranted && userLocation != null) {
            UserMarker(latLng = userLocation)
        }
    }
}


@Composable
private fun ToggleMarker(
    toggleMarker: ToggleMarker,
    @DrawableRes toggleIconRes: Int,
    scale: Float = 1f,
) {
    val markerState =
        rememberMarkerState(
            key = toggleMarker.toString(),
            position = LatLng(toggleMarker.latitude, toggleMarker.longitude)
        )
    key(toggleMarker) {
        MarkerComposable(
            state = markerState
        ) {
            Icon(
                painter = painterResource(toggleIconRes),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(16.dp * scale)
                    .shadow(1.dp)
            )
        }
    }
    DisposableEffect(toggleMarker) {
        onDispose { markerState.position }
    }
}

// https://velog.io/@gudrmsglgl/Compose-Google-Map
@Composable
private fun SelectedSpecialMarker(
    markerState: MarkerState,
    toggleMarker: ToggleMarker,
    specialMarkerInfo: SpecialMarkerInfo,
    onSpecialInfoClick: (List<String>) -> Unit = { },
    scale: Float = 1f,
) {
    val iconScale = scale * 2.0f
    val recomposeKey = remember { mutableStateListOf(false, false) }
    val painters = specialMarkerInfo.imageUrls.take(2).mapIndexed { index, imageUrl ->
        rememberAsyncImagePainter(
            model = ImageRequest
                .Builder(LocalContext.current)
                .data(imageUrl)
                .allowHardware(false)
                .build(),
            placeholder = painterResource(R.drawable.img_special_info),
            error = painterResource(R.drawable.img_special_info),
            onSuccess = {
                recomposeKey[index] = !recomposeKey[index]
            }
        )
    }

    MarkerComposable(
        state = markerState,
        onClick = {
            onSpecialInfoClick(specialMarkerInfo.imageUrls)
            false
        },
        keys = arrayOf(
            toggleMarker.id,
            specialMarkerInfo,
            recomposeKey[0],
            if (recomposeKey.size > 1) recomposeKey[1] else false,
            scale
        ),
        zIndex = Float.MAX_VALUE
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            MapSpecialInfo(
                painters = painters,
                description = specialMarkerInfo.description,
                modifier = Modifier
                    .noRippleClickable {
                        onSpecialInfoClick(specialMarkerInfo.imageUrls)
                    },
                scale = scale
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
                modifier = Modifier.size(24.dp * iconScale)
            )
        }
    }
}

@Composable
private fun ToggleSpecialMarker(
    markerState: MarkerState,
    toggleMarker: ToggleMarker,
    @DrawableRes toggleIconRes: Int,
    onClick: () -> Unit = { },
    scale: Float = 1f,
) {
    MarkerComposable(
        state = markerState,
        onClick = {
            onClick()
            false
        },
        keys = arrayOf(toggleMarker.id, scale),
        zIndex = 0.0f
    ) {
        Icon(
            painter = painterResource(toggleIconRes),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .size(20.dp * scale)
                .shadow(1.dp)
        )
    }
}

@Composable
private fun BuildingMarker(
    buildingMarker: BuildingMarker,
    isSelected: Boolean = false,
    scale: Float = 1f,
    onClick: (BuildingMarker) -> Unit = { },
) {
    val iconScale = if (isSelected) scale * 2.0f else scale
    val iconSize = if (isSelected) (32.dp * iconScale) else (20.dp * iconScale)
    val fontSize = (14 * scale).coerceAtLeast(10f)

    MarkerComposable(
        onClick = { onClick(buildingMarker); false },
        state = MarkerState(
            position = LatLng(
                buildingMarker.latitude,
                buildingMarker.longitude
            )
        ),
        zIndex = if (isSelected) Float.MAX_VALUE else 0f,
        keys = arrayOf({ buildingMarker.id }, { isSelected }, scale)
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
                modifier = Modifier
                    .shadow(10.dp)
                    .size(iconSize)
            )

            Box {
                Text(
                    text = buildingMarker.name,
                    style = KUBFAndroidTheme.typography.semiBold14.copy(
                        fontSize = fontSize.sp,
                        drawStyle = Stroke(
                            width = 4f * scale,
                        ),
                    ),
                    color = Color.White,
                )
                Text(
                    text = buildingMarker.name,
                    style = KUBFAndroidTheme.typography.semiBold14.copy(
                        fontSize = fontSize.sp
                    ),
                    color = if (isSelected) MainGreen else Color(0xFF5A6860),
                )
            }
        }
    }
}

@Composable
private fun DoorMarker(
    doorMarker: DoorMarker,
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

@Composable
fun UserMarker(
    modifier: Modifier = Modifier,
    latLng: LatLng,
) {
    MarkerComposable(
        state = MarkerState(
            position = LatLng(
                latLng.latitude,
                latLng.longitude
            )
        ),
        anchor = Offset(0.5f, 0.5f),
    ) {
        Box(
            modifier = modifier
                .size(40.dp)
                .background(
                    color = MainGreen.copy(alpha = 0.12f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .border(
                        width = 2.dp,
                        color = Color.White,
                        shape = CircleShape
                    )
                    .background(
                        color = MainGreen,
                        shape = CircleShape
                    )
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
    const val MIN_ZOOM = 16.0f
    const val MAX_ZOOM = 18.0f

    val mapProperties = MapProperties(
        isBuildingEnabled = true,
        isIndoorEnabled = false,
        isMyLocationEnabled = false,
        isTrafficEnabled = false,
        // 카메라가 이동할 수 있는 범위
        latLngBoundsForCameraTarget = LatLngBounds(
            LatLng(
                37.5373,
                127.0656,
            ),
            LatLng(
                37.5450,
                127.0952
            )
        ),
        mapStyleOptions = null,
        mapType = MapType.NORMAL,
        maxZoomPreference = MAX_ZOOM,
        minZoomPreference = MIN_ZOOM,
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
        zoomControlsEnabled = true,
        zoomGesturesEnabled = true
    )
    val mapOptions = GoogleMapOptions().apply {
        mapId(BuildConfig.GOOGLE_MAPS_ID)
    }
}

/**
 * 줌 레벨에 따라 마커의 스케일을 계산합니다.
 * @param currentZoom 현재 줌 레벨
 * @param minZoom 최소 줌 레벨
 * @param maxZoom 최대 줌 레벨
 * @return 0.6 ~ 1.0 사이의 스케일 값
 */
private fun calculateMarkerScale(
    currentZoom: Float,
    minZoom: Float,
    maxZoom: Float,
): Float {
    val minScale = 0.8f
    val maxScale = 1.0f
    val normalizedZoom = ((currentZoom - minZoom) / (maxZoom - minZoom)).coerceIn(0f, 1f)
    return minScale + (normalizedZoom * (maxScale - minScale))
}