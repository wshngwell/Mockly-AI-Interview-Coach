package com.example.interviewaicoach.data.remote.dto.dtoForAudio

import com.example.interviewaicoach.data.remote.dto.dtoForAudio.ContentPartDto
import com.google.gson.annotations.SerializedName

data class ChatMessageForAudioDto(
    @SerializedName("role") val role: String,
    @SerializedName("content") val content: List<ContentPartDto>
)