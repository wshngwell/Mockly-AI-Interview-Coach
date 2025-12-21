package com.example.interviewaicoach.data.remote.dto.dtoForText

import com.google.gson.annotations.SerializedName

data class ChoiceTextDto(
    @SerializedName("message")
    val message: ChatMessageForTextDto? = null
)