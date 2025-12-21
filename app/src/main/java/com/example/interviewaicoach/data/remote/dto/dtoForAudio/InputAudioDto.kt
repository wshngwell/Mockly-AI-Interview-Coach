package com.example.interviewaicoach.data.remote.dto.dtoForAudio

import com.google.gson.annotations.SerializedName

data class InputAudioDto(
    @SerializedName("data") val data: String,
    @SerializedName("format") val format: String = "wav"
)