package com.ganaljigi.kubf.mapper

import com.ganaljigi.kubf.data.remote.response.home.HomeResponseDto
import com.ganaljigi.kubf.data.remote.response.home.HomeSignificantResponseDto
import com.ganaljigi.kubf.mapper.toBuildingMarkers
import com.ganaljigi.kubf.ui.home.model.BuildingMarker
import com.ganaljigi.kubf.ui.home.model.MapToggle
import com.ganaljigi.kubf.ui.home.model.ToggleMarker
import com.ganaljigi.kubf.ui.home.viewmodel.HomeUiState
import com.ganaljigi.kubf.ui.home.viewmodel.SpecialMarkerInfo
import kotlinx.collections.immutable.toImmutableList

fun HomeResponseDto.toUiState() = HomeUiState(
    buildingMarkers = this.buildings.toBuildingMarkers(),
    curbMarkers = this.curbs.toToggleMarkers(MapToggle.CURB),
    slopeMarkers = this.ramps.toToggleMarkers(MapToggle.SLOPE),
    stairsMarkers = this.stairs.toToggleMarkers(MapToggle.STAIRS),
    specialMarkers = this.significants.toToggleMarkers(MapToggle.SPECIAL_MARK)
        .toImmutableList(),
)

fun HomeSignificantResponseDto.toSpecialMarkerInfo() = SpecialMarkerInfo(
    description = this.description,
    imageUrls = this.imageUrls,
)


fun List<HomeResponseDto.BuildingPin>.toBuildingMarkers() = this.map {
    BuildingMarker(
        id = it.id,
        name = it.name,
        latitude = it.latitude,
        longitude = it.longitude,
    )
}.toImmutableList()

fun List<HomeResponseDto.HomePin>.toToggleMarkers(
    mapToggle: MapToggle,
) = this.map {
    ToggleMarker(
        id = it.id,
        latitude = it.latitude,
        longitude = it.longitude,
        mapToggle = mapToggle
    )
}.toImmutableList()