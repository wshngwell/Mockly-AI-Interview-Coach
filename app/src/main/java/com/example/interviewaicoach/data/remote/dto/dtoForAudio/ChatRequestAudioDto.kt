package com.example.interviewaicoach.data.remote.dto.dtoForAudio

import com.google.gson.annotations.SerializedName

data class ChatRequestAudioDto(
    @SerializedName("model") val model: String,
    @SerializedName("messages") val messages: List<ChatMessageForAudioDto>,
    @SerializedName("stream") val stream: Boolean = false
)