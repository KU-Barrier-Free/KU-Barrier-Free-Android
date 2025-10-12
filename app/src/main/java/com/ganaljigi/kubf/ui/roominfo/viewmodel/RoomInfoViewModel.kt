package com.ganaljigi.kubf.ui.roominfo.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ganaljigi.kubf.ui.roominfo.repository.RoomInfoRepository
import com.ganaljigi.kubf.ui.roominfo.mapper.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomInfoViewModel @Inject constructor(
    private val repository: RoomInfoRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(RoomInfoUiState())
    val uiState: StateFlow<RoomInfoUiState> = _uiState
        .onStart { loadRoomInfo() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = RoomInfoUiState()
        )

    fun loadRoomInfo(
        buildingId: Long = savedStateHandle.get<Long>("buildingId") ?: -1L,
        spaceId: Long = savedStateHandle.get<Long>("spaceId") ?: -1L,
        type: Int? = savedStateHandle.get<Int>("type") /*?: 1*/,
        buildingNameArg: String? = savedStateHandle.get<String>("buildingName")
    ) {
        if (buildingId <= 0 || spaceId <= 0) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            if (type != null) {
                repository.getRoomInfo(buildingId, spaceId, type).fold(
                    onSuccess = { dto ->
                        val mapped = dto.toUiState()
                        _uiState.value = mapped.copy(
                            isLoading = false,
                            buildingName = buildingNameArg ?: mapped.buildingName
                        )
                    },
                    onFailure = { e ->
                        _uiState.update { it.copy(isLoading = false, error = e.message ?: "오류") }
                    }
                )
            } else {
                runCatching {
                    coroutineScope {
                        val r1 = async { repository.getRoomInfo(buildingId, spaceId, 1) }
                        val r0 = async { repository.getRoomInfo(buildingId, spaceId, 0) }
                        r1.await() to r0.await()
                    }
                }.fold(
                    onSuccess = { (res1, res0) ->
                        val mapped1 = res1.getOrNull()?.toUiState()
                        val mapped0 = res0.getOrNull()?.toUiState()

                        if (mapped1 == null && mapped0 == null) {
                            _uiState.update { it.copy(isLoading = false, error = "데이터 없음") }
                            return@fold
                        }

                        val base = mapped1 ?: mapped0!!
                        val other = mapped0 ?: mapped1

                        val merged = base.copy(
                            roomPicUrls = (base.roomPicUrls + (other?.roomPicUrls ?: emptyList())).distinct(),
                            roomName = base.roomName ?: other?.roomName,
                            department = base.department.ifBlank { other?.department ?: "" },
                            departmentNumber = base.departmentNumber.ifBlank { other?.departmentNumber ?: "" },
                            roomType = base.roomType.ifBlank { other?.roomType ?: "" }
                        )

                        _uiState.value = merged.copy(
                            isLoading = false,
                            buildingName = buildingNameArg ?: merged.buildingName
                        )
                    },
                    onFailure = { e ->
                        _uiState.update { it.copy(isLoading = false, error = e.message ?: "오류") }
                    }
                )
            }
        }
    }
    fun retry() = loadRoomInfo()
}