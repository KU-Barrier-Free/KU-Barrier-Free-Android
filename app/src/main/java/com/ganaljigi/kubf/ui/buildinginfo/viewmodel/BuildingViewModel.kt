package com.ganaljigi.kubf.ui.buildinginfo.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.ganaljigi.kubf.DemoData
import com.ganaljigi.kubf.ui.buildinginfo.model.Facility
import com.ganaljigi.kubf.ui.buildinginfo.model.FloorInfo
import com.ganaljigi.kubf.ui.buildinginfo.model.Note
import com.ganaljigi.kubf.ui.buildinginfo.model.Room
import com.ganaljigi.kubf.ui.buildinginfo.model.RoomSearchResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BuildingViewModel @Inject constructor(
) : ViewModel() {
    private val _uiState: MutableStateFlow<BuildingUIState> = MutableStateFlow(BuildingUIState())
    val uiState = _uiState.asStateFlow()

    private var sourceRoom: List<Room> = emptyList()
    fun setBuilding(buildingId: Long, buildingName: String, rooms: List<Room>){
        sourceRoom = rooms
        _uiState.update {
            it.copy(
                currentBuildingId = buildingId,
                currentBuildingName = buildingName,
                query = TextFieldValue(),
                result = persistentListOf()
            )
        }
    }

    fun onQueryChange(v: TextFieldValue){
        _uiState.update { it.copy(query = v) }
        refreshResults()
    }
    fun clearQuery(){
        _uiState.update { it.copy(query = TextFieldValue(), result = persistentListOf()) }
    }

    private fun roomNumberKey(num: String?):Int{
        val digits = num?.filter { it.isDigit() }
        return digits?.toIntOrNull()?:Int.MAX_VALUE
    }
    private fun matchRank(room: Room, q: String): Int{
        val name = room.name ?: ""
        val num = room.number ?: ""
        return when{
            num.startsWith(q,ignoreCase = true) -> 0
            name.startsWith(q, ignoreCase = true) -> 1
            num.contains(q, ignoreCase = true) -> 2
            name.contains(q, ignoreCase = true) -> 3
            else -> Int.MAX_VALUE
        }
    }

    private fun refreshResults() {
        val q = _uiState.value.query.text.trim()
        if(q.isBlank()){
            _uiState.update { it.copy(result = persistentListOf()) }
            return
        }
        val building = _uiState.value.currentBuildingName
        val roomSearchResults:List<RoomSearchResult> =
            sourceRoom.asSequence()
                .filter { r ->
                    val name = r.name.orEmpty()
                    val num = r.number.orEmpty()
                    name.contains(q, ignoreCase = true) || num.contains(q, ignoreCase = true)
                }
                .sortedWith(compareBy<Room>{roomNumberKey(it.number)}
                    .thenBy { it.name.orEmpty() })
                .map { r ->
                    val display = when{
                        !r.name.isNullOrBlank() -> r.name!!
                        !r.number.isNullOrBlank() -> r.number!!
                        else -> "(이름 없음)"
                    }
                    RoomSearchResult(
                        id = r.id ?:0L,
                        name = display,
                        building = _uiState.value.currentBuildingName,
                        room = r
                    )
                }
                .take(50)
                .toPersistentList()
        _uiState.update { it.copy(result = roomSearchResults) }

    }
    fun loadDemoIfEmpty(){
        if(sourceRoom.isEmpty()){
            setBuilding(
                buildingId = 100L,
                buildingName = "경영관",
                rooms = DemoData.rooms
            )
        }
    }

    fun loading(){
        _uiState.update {
            it.copy(
                buildingInfo = it.buildingInfo.copy(
                    name = "경영관",
                    number = 12,
                    department = "경영대학",
                    imageUrl = "h",
                    facilities = listOf(Facility.CAFE, Facility.CONV),
                    doors = listOf(),
                    notes = listOf(Note("못 지나감", listOf("htt")))
                ),
                totalFloor = it.totalFloor.copy(
                    num = 6,
                    floorList = listOf(
                        FloorInfo(1),FloorInfo(2),
                        FloorInfo(3),FloorInfo(4),FloorInfo(5),FloorInfo(6)
                    )
                )
            )
        }
    }
}

