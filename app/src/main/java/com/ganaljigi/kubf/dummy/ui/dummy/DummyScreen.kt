package com.ganaljigi.kubf.dummy.ui.dummy

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ganaljigi.kubf.dummy.ui.dummy.viewmodel.DummyViewModel

@Composable
fun DummyScreen(
    modifier: Modifier = Modifier,
    viewmodel: DummyViewModel = hiltViewModel(),
) {
    val uiState = viewmodel.uiState.collectAsStateWithLifecycle()

    // UI ... 기능 ...
}