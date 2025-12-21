package com.example.interviewaicoach.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ChatRequestDto(
    @SerializedName("model")
    val model: String,
    @SerializedName("messages")
    val messages: List<ChatMessageDto>,
)