package com.example.interviewaicoach.data.remote.dto.dtoForText

import com.google.gson.annotations.SerializedName

data class StreamChoiceDto(
    @SerializedName("delta") val delta: StreamDeltaDto,
)