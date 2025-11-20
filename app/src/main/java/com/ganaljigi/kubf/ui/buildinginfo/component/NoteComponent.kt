package com.ganaljigi.kubf.ui.buildinginfo.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ganaljigi.kubf.ui.buildinginfo.model.Note
import com.ganaljigi.kubf.ui.theme.Gray4
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme



@Composable
fun NoteComponent(
    note: Note,
    onImageClick: (String) -> Unit = {}
) {
    Box(
        modifier = Modifier.padding(horizontal = 16.dp)
    ){
        Column {
            Text(
                text = note.note,
                style = KUBFAndroidTheme.typography.regular14,
                color = Gray4
            )
            Spacer(Modifier.height(12.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(note.imageUrl){ url ->
                    AsyncImage(
                        model = url,
                        contentDescription = "특이사항 이미지",
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .height(84.dp)
                            .clickable { onImageClick(url) }
                    )
                }
            }

        }
    }
}

@Preview
@Composable
private fun NotePreview() {
    //val note = Note("2층에서 구름다리로 나가면 건물 내부로 다시 들어올 수 없음", mutableListOf("https","https"))
   // NoteComponent(note)
}