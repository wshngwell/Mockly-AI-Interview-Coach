package com.example.interviewaicoach.data.remote.dto.dtoForAudio

import com.google.gson.annotations.SerializedName

data class ContentPartDto(
    @SerializedName("type") val type: String,
    @SerializedName("text") val text: String? = null,
    @SerializedName("input_audio") val inputAudio: InputAudioDto? = null
)