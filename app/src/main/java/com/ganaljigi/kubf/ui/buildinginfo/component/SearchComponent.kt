package com.ganaljigi.kubf.ui.buildinginfo.component

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ganaljigi.kubf.ui.buildinginfo.model.RoomSearchResult
import com.ganaljigi.kubf.ui.buildinginfo.viewmodel.BuildingViewModel
import com.ganaljigi.kubf.ui.common.component.KUBFSearchBar
import com.ganaljigi.kubf.ui.theme.Gray3
import com.ganaljigi.kubf.ui.theme.Gray4
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme
import com.ganaljigi.kubf.ui.theme.MainGreen

@Composable
fun SearchPopup(
    modifier: Modifier,
    viewModel: BuildingViewModel = hiltViewModel(),
    onClose: () -> Unit,
    onRoomClick: (RoomSearchResult) -> Unit
) {
    val ui by viewModel.uiState.collectAsStateWithLifecycle()

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused = interactionSource.collectIsFocusedAsState().value
    Box(
        modifier = Modifier
            .width(328.dp)
            .heightIn(min = 302.dp, max = 480.dp)
            .clip(shape = RoundedCornerShape(6))
            .background(Color.White)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "검색",
                    style = KUBFAndroidTheme.typography.medium15,
                )
            }
            KUBFSearchBar(
                modifier = Modifier.padding(horizontal = 16.dp),
                value = ui.query,
                onValueChange = viewModel::onQueryChange,
                placeHolderText = "건물, 편의시설 검색",
                interactionSource = interactionSource,
                isFocused = isFocused,
                onValueCleared = { viewModel.clearQuery() }
            )
            Spacer(Modifier.height(24.dp))
            if (ui.query.text.isNotBlank()) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Text(
                        "결과 ",
                        style = KUBFAndroidTheme.typography.regular13,
                        color = Gray3
                    )
                    Text(
                        "${ui.result.size}",
                        style = KUBFAndroidTheme.typography.regular13,
                        color = MainGreen
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                items(ui.result, key = {it.id to it.isBuilding}){item ->
                    item.room?.let { RoomComponent(room = it){onRoomClick} }
                }
            }
        }
    }
}

@Preview
@Composable
private fun SearchPreview() {
//    var value by remember { MutableStateOf }
//    SearchPopup(TextFieldValue("")){}
}
