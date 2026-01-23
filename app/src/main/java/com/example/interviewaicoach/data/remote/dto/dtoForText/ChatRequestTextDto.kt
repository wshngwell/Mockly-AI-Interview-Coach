package com.example.interviewaicoach.data.remote.dto.dtoForText

import com.google.gson.annotations.SerializedName

data class ChatRequestTextDto(
    @SerializedName("model")
    val model: String,
    @SerializedName("stream")
    val stream: Boolean = true,
    @SerializedName("messages")
    val messages: List<ChatMessageForTextDto>,
)