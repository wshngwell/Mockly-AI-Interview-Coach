package com.example.interviewaicoach.data.remote.dto.dtoForText

import com.google.gson.annotations.SerializedName

data class StreamDeltaDto(
    @SerializedName("content") val content: String?
)