package com.ganaljigi.kubf.ui.buildinginfo.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
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

    private fun refreshResults() {
        val q = _uiState.value.query.text.trim()
        if(q.isBlank()){
            _uiState.update { it.copy(result = persistentListOf()) }
            return
        }
        val building = _uiState.value.currentBuildingName
        val results = mutableListOf<RoomSearchResult>()
        if(building.contains(q, ignoreCase = true)){
            results += RoomSearchResult(
                id = _uiState.value.currentBuildingId,
                name = building,
                building = building,
                isBuilding = true
            )
        }

        sourceRoom.asSequence()
            .filter { r ->
                val rn = r.name ?:""
                val num = r.number ?:""
                rn.contains(q, true) || num.contains(q,true)
            }
            .map { r ->
                val display = when {
                    !r.name.isNullOrBlank() -> r.name!!
                    !r.number.isNullOrBlank() -> r.number!!
                    else -> "(이름 없음)"
                }
                RoomSearchResult(
                    id = r.id?:0L,
                    name = display,
                    building = building,
                    isBuilding = false,
                    room = r
                )
            }
            .take(50)
            .forEach { results +=it }
        _uiState.update { it.copy(result = results.toPersistentList()) }

    }
}