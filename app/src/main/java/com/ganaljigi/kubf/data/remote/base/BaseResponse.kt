package com.ganaljigi.kubf.data.remote.base

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    @SerialName("success") val success: Boolean,
    @SerialName("code") val code: Int,
    @SerialName("message") val message: String,
    @SerialName("result") val result: T
)

fun <T> BaseResponse<T>.handleBaseResponse(): Result<T> =
    when (this.code) {
        200, 1000 -> {
            Result.success(this.result)
        }

        else -> {
            Result.failure(Exception("Handleable Error : code = ${this.code} message = ${this.message}"))
        }
    }
