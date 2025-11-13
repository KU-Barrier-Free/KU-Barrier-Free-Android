@file:OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
package com.ganaljigi.kubf.ui.roominfo.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme
import com.ganalijigi.kubf.R
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RoomPic(
    roomPicUrls: List<String>,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { roomPicUrls.size })
    var showViewer by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxWidth()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = roomPicUrls.isNotEmpty()) { showViewer = true }
        ) { page ->
            AsyncImage(
                model = roomPicUrls[page],
                contentDescription = "강의실 사진",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (roomPicUrls.size > 1) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 12.dp)
                    .background(
                        Color(0xFF212121).copy(alpha = 0.6f),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .zIndex(2f)
            ) {
//                Row(
//                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(
//                        text = "${pagerState.currentPage + 1}",
//                        style = KUBFAndroidTheme.typography.semiBold13.copy(color = Color.White)
//                    )
//                    Spacer(modifier = Modifier.width(1.5.dp))
//                    Text(
//                        text = "/",
//                        style = KUBFAndroidTheme.typography.regular13.copy(color = Color.White)
//                    )
//                    Spacer(modifier = Modifier.width(1.5.dp))
//                    Text(
//                        text = "${roomPicUrls.size}",
//                        style = KUBFAndroidTheme.typography.regular13.copy(color = Color.White)
//                    )
//                }
                PagerCounter(pagerState = pagerState, total = roomPicUrls.size)
            }
        }
    }

    if (showViewer) {
        Dialog(
            onDismissRequest = { showViewer = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            val insetPadding = WindowInsets.systemBars.asPaddingValues()

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f))
            ) {
                val viewPager = rememberPagerState(
                    initialPage = pagerState.currentPage,
                    pageCount = { roomPicUrls.size }
                )

                //확대 하는 중에는 페이지 넘김 안됨
                var canSwipe by remember { mutableStateOf(true) }

                HorizontalPager(
                    state = viewPager,
                    modifier = Modifier.fillMaxSize(),
                    userScrollEnabled = canSwipe
                ) { page ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        ZoomableImage(
                            url = roomPicUrls[page],
                            modifier = Modifier
                                .fillMaxSize(),
                            onBaseScale = { atBase -> canSwipe = atBase }
                        )

                        if (roomPicUrls.size > 1) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(
                                        bottom = insetPadding.calculateBottomPadding() + 12.dp
                                    )
                                    .background(
                                        Color(0xFF212121).copy(alpha = 0.6f),
                                        shape = RoundedCornerShape(20.dp)
                                    )
                                    .zIndex(3f)
                            ) {
                                PagerCounter(pagerState = viewPager, total = roomPicUrls.size)
                            }
                        }
                    }
                }

                IconButton(
                    onClick = { showViewer = false },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(
                            top = insetPadding.calculateTopPadding() + 12.dp,
                            end = 16.dp
                        )
                        .size(24.dp)
                        .zIndex(1f)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_roominfo_picviewer),
                        contentDescription = "닫기",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ZoomableImage(
    url: String,
    modifier: Modifier = Modifier,
    onBaseScale: (Boolean) -> Unit = {}
) {
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    LaunchedEffect(Unit) {
        onBaseScale(true)
    }

    val transformState = rememberTransformableState { zoomChange, panChange, _ ->
        val newScale = (scale * zoomChange).coerceIn(1f, 4f)
        scale = newScale
        if (newScale > 1f) {
            offset += panChange
            onBaseScale(false)
        } else {
            offset = Offset.Zero
            onBaseScale(true)
        }
    }

    val doubleTapReset = Modifier.pointerInput(Unit) {
        detectTapGestures(
            onDoubleTap = {
                scale = 1f
                offset = Offset.Zero
                onBaseScale(true)
            }
        )
    }

    val zoomModifier =
        if (scale > 1f) {
            Modifier.transformable(
                state = transformState,
                canPan = { true }
            )
        } else {
            Modifier
        }

    AsyncImage(
        model = url,
        contentDescription = "이미지 확대",
        contentScale = ContentScale.Fit,
        modifier = modifier
            .graphicsLayer {
                translationX = offset.x
                translationY = offset.y
                scaleX = scale
                scaleY = scale
            }
            .then(zoomModifier)
            .then(doubleTapReset)
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PagerCounter(
    pagerState: androidx.compose.foundation.pager.PagerState,
    total: Int,
    modifier: Modifier = Modifier
) {
    val current by remember(pagerState) { derivedStateOf { pagerState.currentPage } }

    Box(
        modifier = modifier
            .background(Color(0xFF212121).copy(alpha = 0.6f), RoundedCornerShape(20.dp))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${current + 1}",
                style = KUBFAndroidTheme.typography.semiBold13.copy(color = Color.White)
            )
            Spacer(modifier = Modifier.width(1.5.dp))
            Text(
                text = "/",
                style = KUBFAndroidTheme.typography.regular13.copy(color = Color.White)
            )
            Spacer(modifier = Modifier.width(1.5.dp))
            Text(
                text = "$total",
                style = KUBFAndroidTheme.typography.regular13.copy(color = Color.White)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RoomPicPreview() {
    RoomPic(
        roomPicUrls = listOf(
            "https://i.pinimg.com/1200x/10/dc/2e/10dc2ece8b542854d0e276a1114d6190.jpg",
            "https://i.pinimg.com/1200x/d9/5e/3f/d95e3f592893bd3df9e62df37c831638.jpg"
        )
    )
}