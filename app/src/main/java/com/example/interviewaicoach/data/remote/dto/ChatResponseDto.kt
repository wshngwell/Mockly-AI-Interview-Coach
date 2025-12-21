package com.example.interviewaicoach.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ChatResponseDto(
    @SerializedName("choices")
    val choices: List<ChoiceDto>
)
