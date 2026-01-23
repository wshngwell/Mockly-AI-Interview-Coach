package com.example.interviewaicoach.data.remote.dto.dtoForText

import com.google.gson.annotations.SerializedName

data class ChatMessageForTextDto(
    @SerializedName("role")
    val role: String?,
    @SerializedName("content")
    val content: String
)