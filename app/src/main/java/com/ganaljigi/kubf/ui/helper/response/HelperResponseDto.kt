package com.ganaljigi.kubf.ui.helper.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//상태
@Serializable
data class HelperNoticeResponseDto(
    @SerialName("success")
    val success: Boolean,
    @SerialName("code")
    val code: Int,
    @SerialName("message")
    val message: String,
    @SerialName("result")
    val result: List<HelperNoticeDto>? = null
)

//개별 공지
@Serializable
data class HelperNoticeDto(
    @SerialName("title")
    val title: String,
    @SerialName("date")
    val date: String,
    @SerialName("url")
    val url: String,
    @SerialName("number")
    val number: Int
)