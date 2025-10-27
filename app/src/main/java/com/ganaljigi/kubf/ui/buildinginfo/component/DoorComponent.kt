package com.ganaljigi.kubf.ui.buildinginfo.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil3.compose.AsyncImage
import com.ganaljigi.kubf.ui.buildinginfo.model.Door
import com.ganaljigi.kubf.ui.theme.Gray2
import com.ganaljigi.kubf.ui.theme.Gray3
import com.ganaljigi.kubf.ui.theme.Gray4
import com.ganaljigi.kubf.ui.theme.MainGreen
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme
import kotlin.math.absoluteValue


/**
 * - 출입문 컴포넌트 - 문 사진과 휠체어 가능 여부,
 * - 가로로 스크롤 가능
 */
@Composable
fun DoorComponent(doors: List<Door>) {
    var previewDoor by remember { mutableStateOf<Door?>(null) }
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        items(doors, key = { it.label }) { door ->
            DoorCard(door) {
                previewDoor = door
            }
        }

    }
    previewDoor?.let { door ->
        DoorImageDialog(
            door = door,
            onDismiss = { previewDoor = null }
        )
    }
}

@Composable
fun DoorImageDialog(door: Door, onDismiss: () -> Unit) {
    val images = door.imageUrl.ifEmpty { listOf<String>() }
    Dialog(
        onDismissRequest =
            onDismiss, properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(Modifier.fillMaxWidth()) {
            val pagerState =
                rememberPagerState(pageCount = { images.size })
            HorizontalPager(
                state =
                    pagerState, modifier = Modifier.fillMaxWidth()
            ) { page ->
                TransformableImage(
                    modifier = Modifier.fillMaxWidth(),
                    images.getOrNull(page)
                )
            }
            if (images.size > 1) {
                Text(
                    text = "${pagerState.currentPage + 1}/${images.size}",
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp),
                    textAlign = TextAlign.Center,
                    style = KUBFAndroidTheme.typography.medium13
                )
            }
        }
    }
}

@Composable
fun TransformableImage(modifier: Modifier = Modifier, imageUrl: String?) {
    var containerSize by remember { mutableStateOf(IntSize.Zero) }
    var imageSize by remember { mutableStateOf(IntSize.Zero) }
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Companion.Zero) }
    val scaledWidth by remember(imageSize, scale) { derivedStateOf { imageSize.width * scale } }
    val scaledHeight by remember(imageSize, scale) { derivedStateOf { imageSize.height * scale } }
    val imageRect by remember(
        scaledHeight,
        scaledWidth,
        offset,
        containerSize
    ) {
        derivedStateOf {
            val cx = containerSize.width / 2f
            val cy = containerSize.height / 2f
            Rect(
                offset = Offset(
                    x = cx - scaledWidth / 2f + offset.x,
                    y = cy - scaledHeight / 2f + offset.y
                ), size = Size(scaledWidth, scaledHeight)
            )
        }
    }
    Box(
        modifier = modifier
            .onSizeChanged { containerSize = it }
            .pointerInput(Unit) {
                detectTransformGestures { centroid, panChange, zoomChange, _ ->
                    if (!imageRect.contains(centroid)) return@detectTransformGestures
                    if (zoomChange != 1f) {
                        val newScale = (scale * zoomChange).coerceAtLeast(1f)
                        val currentCenter = Offset(
                            x = containerSize.width / 2f + offset.x,
                            y = containerSize.height / 2f + offset.y
                        )
                        val relative = centroid - currentCenter
                        val scaleChange = newScale / scale
                        var newOffsetX = offset.x + relative.x * (1f - scaleChange)
                        var newOffsetY = offset.y + relative.y * (1f - scaleChange)
                        newOffsetX += panChange.x
                        newOffsetY += panChange.y
                        scale = newScale
                        val maxOffsetX =
                            ((imageSize.width * newScale - containerSize.width) / 2f).coerceAtLeast(
                                0f
                            )
                        val maxOffsetY =
                            ((imageSize.height * newScale - containerSize.height) / 2f).coerceAtLeast(
                                0f
                            )
                        offset = if (newScale == 1f) {
                            Offset.Zero
                        } else {
                            Offset(
                                x = newOffsetX.coerceIn(-maxOffsetX, maxOffsetX),
                                y = newOffsetY.coerceIn(-maxOffsetY, maxOffsetY)
                            )
                        }
                        return@detectTransformGestures
                    }
                    val maxOffsetX = ((scaledWidth - containerSize.width) / 2f).absoluteValue
                    val maxOffsetY =
                        ((scaledHeight - containerSize.height) / 2f).absoluteValue
                    offset = if (scale == 1f) {
                        Offset.Zero
                    } else {
                        Offset(
                            x = (offset.x + panChange.x).coerceIn(-maxOffsetX, maxOffsetX),
                            y = (offset.y + panChange.y).coerceIn(-maxOffsetY, maxOffsetY)
                        )
                    }
                }
            }) {
        AsyncImage(
            modifier = Modifier
                .align(Alignment.Center)
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    translationX = offset.x,
                    translationY = offset.y
                )
                .onGloballyPositioned { coord -> imageSize = coord.size },
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
    }
}

/**
 * 출입문 정보
 * - 문 사진
 * - 휠체어 여부
 */
@Composable
fun DoorCard(door: Door, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(80.dp)
            .background(Color.White)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(80.dp)
            ) {
                AsyncImage(
                    model = door.imageUrl.first(),
                    contentDescription = "${door.label}",
                    modifier = Modifier
                        .matchParentSize()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Gray2),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(4.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Gray4)
                        .height(16.dp)
                        .widthIn(min = 16.dp)
                        .wrapContentWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text( // 출입문 이름
                        text = door.label,
                        style = KUBFAndroidTheme.typography.medium14,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 3.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "휠체어 진입",
                style = KUBFAndroidTheme.typography.regular13
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (door.wheelchair) "가능 O" else "불가능 X",
                style = KUBFAndroidTheme.typography.semiBold14.copy(
                    color = if (door.wheelchair) MainGreen else Gray3
                )
            )
        }
    }
}

@Preview
@Composable
private fun DoorPreview() {
//    val doors = mutableListOf(Door("https://", "창의관", "A", false))
//    doors.add(Door("https://", "창의관", "B", true))
//    DoorComponent(doors)
}