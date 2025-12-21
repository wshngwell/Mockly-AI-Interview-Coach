package com.example.interviewaicoach.data.remote

import com.example.interviewaicoach.BuildConfig
import com.example.interviewaicoach.data.remote.dto.dtoForAudio.ChatRequestAudioDto
import com.example.interviewaicoach.data.remote.dto.dtoForAudio.ChatResponseAudioDto
import com.example.interviewaicoach.data.remote.dto.dtoForText.ChatRequestTextDto
import com.example.interviewaicoach.data.remote.dto.dtoForText.ChatResponseTextDto
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Streaming

interface ApiService {

    @Streaming
    @POST("chat/completions")
    suspend fun sendPromt(
        @Body request: ChatRequestTextDto,
        @Header(HEADER_CONTENT_TYPE_KEY) contentType: String = HEADER_CONTENT_TYPE_KEY_VALUE,
        @Header(HEADER_AUTHORIZATION_KEY) apiKey: String = API_KEY_VALUE,

        ): ResponseBody

    @POST("chat/completions")
    suspend fun transcribeAudio(
        @Body request: ChatRequestAudioDto,
        @Header(HEADER_CONTENT_TYPE_KEY) contentType: String = HEADER_CONTENT_TYPE_KEY_VALUE,
        @Header(HEADER_AUTHORIZATION_KEY) apiKey: String = API_KEY_VALUE,
    ): ChatResponseAudioDto

    companion object {
        private const val HEADER_AUTHORIZATION_KEY = "Authorization"
        private const val HEADER_CONTENT_TYPE_KEY = "Content-Type"
        private const val HEADER_CONTENT_TYPE_KEY_VALUE = "application/json"
        private const val API_KEY_VALUE = "Bearer ${BuildConfig.API_KEY}"
    }
}