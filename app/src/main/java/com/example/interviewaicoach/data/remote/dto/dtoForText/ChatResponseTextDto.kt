package com.example.interviewaicoach.data.remote.dto.dtoForText

import com.google.gson.annotations.SerializedName

data class ChatResponseTextDto(
    @SerializedName("choices")
    val choices: List<ChoiceTextDto>
)