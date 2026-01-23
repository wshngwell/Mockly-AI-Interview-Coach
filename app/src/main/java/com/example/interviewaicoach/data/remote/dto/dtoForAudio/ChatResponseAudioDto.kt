package com.example.interviewaicoach.data.remote.dto.dtoForAudio

import com.google.gson.annotations.SerializedName

data class ChatResponseAudioDto(
    @SerializedName("choices")
    val choices: List<ChoiceAudioDto>
)
