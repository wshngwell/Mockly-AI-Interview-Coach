package com.example.interviewaicoach.data.remote

import com.example.interviewaicoach.BuildConfig
import com.example.interviewaicoach.data.remote.dto.ChatRequestDto
import com.example.interviewaicoach.data.remote.dto.ChatResponseDto
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("chat/completions")
    suspend fun sendPromt(
        @Body request: ChatRequestDto,
        @Header(HEADER_CONTENT_TYPE_KEY) contentType: String = HEADER_CONTENT_TYPE_KEY_VALUE,
        @Header(HEADER_AUTHORIZATION_KEY) apiKey: String = API_KEY_VALUE,

        ): ChatResponseDto


    companion object {
        private const val HEADER_AUTHORIZATION_KEY = "Authorization"
        private const val HEADER_CONTENT_TYPE_KEY = "Content-Type"
        private const val HEADER_CONTENT_TYPE_KEY_VALUE = "application/json"
        private const val API_KEY_VALUE = "Bearer ${BuildConfig.API_KEY}"
    }
}