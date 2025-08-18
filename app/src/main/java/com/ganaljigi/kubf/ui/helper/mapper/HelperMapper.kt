package com.ganaljigi.kubf.ui.helper.mapper

import android.R.id.message
import coil3.util.CoilUtils.result
import com.ganaljigi.kubf.ui.helper.response.HelperNoticeDto
import com.ganaljigi.kubf.ui.helper.response.HelperNoticeResponseDto
import com.ganaljigi.kubf.ui.helper.viewmodel.HelperUiState
import com.ganaljigi.kubf.ui.helper.viewmodel.NoticeUi
import kotlin.Result.Companion.success


//상태? 에 대한~것!
fun HelperNoticeResponseDto.toUiState(): HelperUiState {
    if (!success || result == null) {
        return HelperUiState(
            isLoading = false,
            error = message,
            notices = emptyList()
        )
    }

    val items = result.map { it.toUi() }

    return HelperUiState(
        //상태?
        isLoading = false,
        error = null,
        notices = items
    )
}

//개별 공지에 대한~것!
fun HelperNoticeDto.toUi(): NoticeUi = NoticeUi(
    title = title,
    date = date,
    url = url
)

//저녁 뭐먹을까