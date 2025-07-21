package com.ganaljigi.kubf.ui.helper.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ganaljigi.kubf.ui.helper.component.fetchNotices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.compose.runtime.State

data class Notice(
    val title: String,
    val date: String,
    val url: String
)

class HelperViewModel : ViewModel() {
    private val _notices = mutableStateOf<List<Notice>>(emptyList())
    val notices: State<List<Notice>> = _notices

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val list = fetchNotices()
            withContext(Dispatchers.Main) {
                _notices.value = list
            }
        }
    }
}
