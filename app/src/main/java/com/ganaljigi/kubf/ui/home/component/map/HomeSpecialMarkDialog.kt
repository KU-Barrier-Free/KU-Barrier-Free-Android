package com.ganaljigi.kubf.ui.home.component.map

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil3.compose.AsyncImage
import com.ganaljigi.kubf.ui.util.conditionalModifier

@Composable
fun HomeSpecialMarkDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: (Boolean) -> Unit = {},
    imageUrls: List<String> = emptyList(),
) {
    Dialog(
        onDismissRequest = { onDismissRequest(false) },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Row(
            modifier = modifier
                .heightIn(max = 272.dp)
                .padding(horizontal = 10.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            imageUrls.forEach { imageUrl ->
                AsyncImage(
                    modifier = Modifier
                        .conditionalModifier(
                            condition = imageUrls.size > 1,
                            modifierIfTrue = Modifier.width(180.dp),
                            modifierIfFalse = Modifier.size(272.dp)
                        )
                        .clip(RoundedCornerShape(10.dp)),
                    model = imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
            }
        }
    }
}


@Preview
@Composable
private fun HomeSpecialMarkDialogPreview() {

}