package com.ganaljigi.kubf.ui.buildinginfo.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ganaljigi.kubf.data.mock.DummySpacesJson
import com.ganaljigi.kubf.data.remote.base.BaseResponse
import com.ganaljigi.kubf.data.repository.BuildingInfoRepository
import com.ganaljigi.kubf.ui.buildinginfo.mapper.toUiPair
import com.ganaljigi.kubf.ui.buildinginfo.model.BuildingInfo
import com.ganaljigi.kubf.ui.buildinginfo.model.Door
import com.ganaljigi.kubf.ui.buildinginfo.model.Facility
import com.ganaljigi.kubf.ui.buildinginfo.model.FloorInfo
import com.ganaljigi.kubf.ui.buildinginfo.model.Note
import com.ganaljigi.kubf.ui.buildinginfo.model.Room
import com.ganaljigi.kubf.ui.buildinginfo.model.RoomSearchResult
import com.ganaljigi.kubf.ui.buildinginfo.model.TotalFloor
import com.ganaljigi.kubf.ui.buildinginfo.response.SpacesDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class BuildingViewModel @Inject constructor( private val repo: BuildingInfoRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<BuildingUIState> = MutableStateFlow(BuildingUIState())
    val uiState = _uiState.asStateFlow()

    private var sourceRoom: List<Room> = emptyList()

    fun init(buildingId: Long){
        viewModelScope.launch {
            _uiState.update { it.copy(isSearching = false) }
            runCatching { repo.fetchBuildingSpaces(buildingId) }
                .onSuccess { (info, total) ->
                    sourceRoom = total.floorList.flatMap { it.rooms }
                    _uiState.update {
                        it.copy(
                            currentBuildingId = buildingId,
                            currentBuildingName = info.name,
                            buildingInfo = info,
                            totalFloor = total
                        )
                    }
                }
        }
    }

    fun loadMockFromJson(){
        viewModelScope.launch {
            _uiState.update { it.copy(isSearching = true) }
            runCatching {
                val json = Json { ignoreUnknownKeys = true }
                val resp: BaseResponse<SpacesDto> = json.decodeFromString(
                    BaseResponse.serializer(SpacesDto.serializer()),
                    DummySpacesJson.JSON
                )
                resp.result
            }.onSuccess { dto ->
                val (info, total) = dto.toUiPair()
                sourceRoom = total.floorList.flatMap { it.rooms }
                _uiState.update {
                    it.copy(
                        currentBuildingId = info.id,
                        currentBuildingName = info.name,
                        buildingInfo = info,
                        totalFloor = total,
                        query = TextFieldValue(),
                        result = persistentListOf(),
                        isSearching = false
                    )
                }
            }.onFailure { e ->
                _uiState.update { it.copy(isSearching = false) }
                e.printStackTrace()
            }
        }
    }

    fun loadMock(buildingId: Long = 100L){ // 임시데이터
        val mockInfo = BuildingInfo(
            id = buildingId,
            name = "경영관",
            number = 2,
            department = "경영대학 / 경영학부",
            imageUrl = "https://cdn.pixabay.com/photo/2024/06/17/14/58/school-8835808_1280.jpg",
            lecture = true,
            facilities = listOf(
                Facility.CAFE, Facility.CONV, Facility.PRINT, Facility.REST, Facility.SERVICE, Facility.BANK
            ),
            doors = listOf(
                Door(
                    id = 1,
                    imageUrl = listOf("https://cdn.pixabay.com/photo/2024/06/17/14/58/school-8835808_1280.jpg"),
                    label = "A",
                    wheelchair = true,
                    latitude = 37.54012, longitude = 127.07371
                ),
                Door(
                    id = 2,
                    imageUrl = listOf("https://cdn.pixabay.com/photo/2024/06/17/14/58/school-8835808_1280.jpg"),
                    label = "B",
                    wheelchair = false,
                    latitude = 37.54028, longitude = 127.07386
                )
            ),
            notes = listOf(
                Note(id = 1, note = "지하1층 엘리베이터 점검 (8/31까지)", imageUrl = emptyList()),
                Note(id = 2, note = "주차장 혼잡: 제2주차장 이용 권장", imageUrl = emptyList())
            ),
            latitude = 37.54000,
            longitude = 127.07350
        )

        val floorB1 = FloorInfo(
            floorLabel = "B1",
            imageUrl = listOf("https://cdn.pixabay.com/photo/2024/06/17/14/58/school-8835808_1280.jpg"),
            facilities = listOf(Facility.PARK, Facility.REST),
            rooms = listOf(
                Room(id = 101, number = "B101", name = "기계실", isLecture = false, comment = "관계자 외 출입금지"),
                Room(id = 102, number = "B102", name = "주차장 연결 출입구", isLecture = false)
            )
        )
        val floor1 = FloorInfo(
            floorLabel = "1",
            imageUrl = listOf("https://cdn.pixabay.com/photo/2024/06/17/14/58/school-8835808_1280.jpg"),
            facilities = listOf(Facility.CAFE, Facility.CONV, Facility.SERVICE),
            rooms = listOf(
                Room(id = 201, number = "101", name = "전산실습실", isLecture = true, roomImages = listOf("https://cdn.pixabay.com/photo/2024/06/17/14/58/school-8835808_1280.jpg")),
                Room(id = 202, number = "102", name = "세미나실", isLecture = false, roomImages = listOf("https://cdn.pixabay.com/photo/2024/06/17/14/58/school-8835808_1280.jpg")),
                Room(id = 203, number = "103", name = "학생회실", isLecture = false)
            )
        )
        val floor2 = FloorInfo(
            floorLabel = "2",
            imageUrl = listOf("https://cdn.pixabay.com/photo/2024/06/17/14/58/school-8835808_1280.jpg"),
            facilities = listOf(Facility.PRINT),
            rooms = listOf(
                Room(id = 301, number = "201", name = "전산실습실", isLecture = true),
                Room(id = 302, number = "202", name = "일반강의실", isLecture = true, roomImages = listOf("https://cdn.pixabay.com/photo/2024/06/17/14/58/school-8835808_1280.jpg","https://cdn.pixabay.com/photo/2024/06/17/14/58/school-8835808_1280.jpg")),
                Room(id = 303, number = "203", name = "세미나실", isLecture = false)
            )
        )

        val total = TotalFloor(
            num = 3,
            floorList = listOf(floorB1, floor1, floor2)
        )

        // 검색소스 업데이트
        sourceRoom = total.floorList.flatMap { it.rooms }

        // UI 반영
        _uiState.update {
            it.copy(
                currentBuildingId = buildingId,
                currentBuildingName = mockInfo.name,
                buildingInfo = mockInfo,
                totalFloor = total,
                query = TextFieldValue(),
                result = persistentListOf(),
                isSearching = false
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

    private fun refreshResults() {
        val q = _uiState.value.query.text.trim()
        if(q.isBlank()){
            _uiState.update { it.copy(result = persistentListOf()) }
            return
        }
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
}

