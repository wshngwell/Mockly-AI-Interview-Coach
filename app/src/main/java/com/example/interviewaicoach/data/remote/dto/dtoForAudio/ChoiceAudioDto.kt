package com.example.interviewaicoach.data.remote.dto.dtoForAudio

import com.example.interviewaicoach.data.remote.dto.dtoForText.ChatMessageForTextDto
import com.google.gson.annotations.SerializedName

data class ChoiceAudioDto(
    @SerializedName("message")
    val message: ChatMessageForTextDto? = null
)
