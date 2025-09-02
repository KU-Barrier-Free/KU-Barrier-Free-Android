package com.ganaljigi.kubf.mapper

import com.ganaljigi.kubf.data.remote.response.building.BuildingSummaryResponseDto
import com.ganaljigi.kubf.ui.common.model.DoorInfo
import com.ganaljigi.kubf.ui.common.model.fromLabel
import com.ganaljigi.kubf.ui.home.model.DoorMarker
import com.ganaljigi.kubf.ui.home.viewmodel.HomeBuildingInfo
import kotlinx.collections.immutable.toPersistentList

fun BuildingSummaryResponseDto.toHomeBuildingInfo() = HomeBuildingInfo(
    id = id,
    name = name,
    buildingNumber = number,
    latitude = latitude,
    longitude = longitude,
    convenienceList = facilityPurposes.mapNotNull { fromLabel(it) }.toPersistentList(),
    doorInfoList = doorInfos.map { it.toDoorInfo() }.toPersistentList()
)

fun BuildingSummaryResponseDto.DoorInfoDto.toDoorInfo() = DoorInfo(
    id = this.id,
    imageUrl = this.imageUrl.first(),
    label = label,
    latitude = latitude,
    longitude = longitude,
    isWheelchairAccessible = wheelchair,
    description = "",
    imageUrls = this.imageUrl,
)

fun BuildingSummaryResponseDto.DoorInfoDto.toDoorMarker() = DoorMarker(
    id = this.id,
    label = this.label,
    latitude = this.latitude,
    longitude = this.longitude,
    isWheelChairAccessible = this.wheelchair
)

fun BuildingSummaryResponseDto.toDoorMarkers() = doorInfos.map { it.toDoorMarker() }