package com.ganaljigi.kubf.ui.roominfo.mapper

import com.ganaljigi.kubf.ui.roominfo.response.RoomInfoResponseDto
import com.ganaljigi.kubf.ui.roominfo.viewmodel.RoomInfoUiState

fun RoomInfoResponseDto.toUiState(): RoomInfoUiState {
    if (!success || result == null) {
        return RoomInfoUiState(
            isLoading = false,
            error = message
        )
    }

    val r = result
    val info = r.roomInfo

    return RoomInfoUiState(
        // 상태??
        isLoading = false,
        error = null,

        //DefalutInformation 기본 정보
        roomNumber = r.roomNumber,
        roomName = r.roomName,
        lecture = r.lecture,
        capacity = r.capacity,
        area = r.area,
        floorSpace = r.floorSpace,
        roomType = r.roomType,
        roomComment = r.roomComment.orEmpty(),
        department = r.department,
        departmentNumber = r.departmentNumber,

        //이미지(ROOM만 쓸거임)
        roomPicUrls = r.images
            .sortedBy { it.imageType != "ROOM" }
            .map { it.imageUrl }
            .distinct(),

        // roomInfo가 null이면 전부 false로 (이렇게 하는게 좋다고 지피띠니가 말해줌
        allInOne = info?.allInOne ?: false,
        cinemaSeat = info?.cinemaSeat ?: false,
        oneSeat = info?.oneSeat ?: false,
        twoSeat = info?.twoSeat ?: false,
        multiSeat = info?.multiSeat ?: false,
        panel = info?.panel ?: false,
        backOfChair = info?.backOfChair ?: false,
        wheelChair = info?.wheelChair ?: false,
        wheelchairTable = info?.wheelchairTable ?: false,
        computerTable = info?.computerTable ?: false,
        frontDoor = info?.frontDoor ?: false,
        backDoor = info?.backDoor ?: false,

        //roomInfo null 여부
        hasRoomInfo = (info != null)
    )
}