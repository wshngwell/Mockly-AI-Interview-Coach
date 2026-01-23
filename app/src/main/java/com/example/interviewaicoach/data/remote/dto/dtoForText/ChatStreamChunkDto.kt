package com.example.interviewaicoach.data.remote.dto.dtoForText

import com.example.interviewaicoach.data.remote.dto.dtoForText.StreamChoiceDto
import com.google.gson.annotations.SerializedName

data class ChatStreamChunkDto(
    @SerializedName("choices") val choices: List<StreamChoiceDto>,
)