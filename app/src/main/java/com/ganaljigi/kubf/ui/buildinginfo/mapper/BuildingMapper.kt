package com.ganaljigi.kubf.ui.buildinginfo.mapper

import com.ganaljigi.kubf.ui.buildinginfo.model.BuildingInfo
import com.ganaljigi.kubf.ui.buildinginfo.model.Door
import com.ganaljigi.kubf.ui.buildinginfo.model.Facility
import com.ganaljigi.kubf.ui.buildinginfo.model.FloorInfo
import com.ganaljigi.kubf.ui.buildinginfo.model.Note
import com.ganaljigi.kubf.ui.buildinginfo.model.Room
import com.ganaljigi.kubf.ui.buildinginfo.response.DoorInfoDto
import com.ganaljigi.kubf.ui.buildinginfo.response.FloorDto
import com.ganaljigi.kubf.ui.buildinginfo.response.NoteDto
import com.ganaljigi.kubf.ui.buildinginfo.response.SpaceSummaryDto
import com.ganaljigi.kubf.ui.buildinginfo.response.SpacesDto

private val facilityMap: Map<String, Facility> = mapOf(
    "카페" to Facility.CAFE,
    "휴게실" to Facility.REST,
    "은행" to Facility.BANK,
    "편의점" to Facility.CONV,
    "복사기" to Facility.PRINT,
    "K-Cube" to Facility.KCUBE,
    "IT-서비스센터" to Facility.SERVICE,
    "주차장" to Facility.PARK
)

fun String.toFacilityOrNull(): Facility? = facilityMap[this]

fun DoorInfoDto.toUi() = Door(
    id = id,
    label = label.orEmpty(),
    imageUrl = imageUrl.orEmpty(),
    wheel = wheel,
    latitude = latitude,
    longitude = longitude
)

fun NoteDto.toUi() = Note(
    id = id,
    note = note.orEmpty(),
    imageUrl = imageUrl.orEmpty()
)

fun SpaceSummaryDto.toRoomUi(): Room {
    val roomImgs = roomImages.filter { it.imageType.equals("ROOM",true) }.map { it.imageUrl }
    val doorImgs = roomImages.filter { it.imageType.equals("DOOR",true) }.map { it.imageUrl }
    return Room(
        id = id,
        number = roomNumber.orEmpty(),
        name = roomName.orEmpty(),
        isLecture = isLecture,
        comment = comment.orEmpty(),
        roomImages = roomImgs,
        doorImages = doorImgs
    )
}

fun FloorDto.toUi(): FloorInfo = FloorInfo(
    floorLabel = floor,
    imageUrl = drawings.orEmpty(),
    facilities = purposes.orEmpty().mapNotNull { it.toFacilityOrNull() },
    rooms = spaceSummaries.orEmpty().map{ it.toRoomUi() }
)

fun SpacesDto.toBuildingInfoUi(): BuildingInfo = BuildingInfo(
    id = id,
    name = name,
    number = number,
    department = department.orEmpty(),
    imageUrl = image.orEmpty(),
    lecture = lecture,
    facilities = facility.orEmpty().mapNotNull { it.toFacilityOrNull() },
    doors = doorInfos.orEmpty().map { it.toUi() },
    notes = notes.orEmpty().map { it.toUi() },
    latitude = latitude,
    longitude = longitude
)