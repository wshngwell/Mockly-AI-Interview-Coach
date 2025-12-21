package com.example.interviewaicoach.data.repositories

import android.util.Base64
import com.example.interviewaicoach.data.mappers.toChatRequestForAudio
import com.example.interviewaicoach.data.remote.ApiService
import com.example.interviewaicoach.data.remote.parseToLoadingException
import com.example.interviewaicoach.domain.entities.LoadingException
import com.example.interviewaicoach.domain.entities.TResult
import com.example.interviewaicoach.domain.entities.speechToTextEntities.SpeechToTextResultEntity
import com.example.interviewaicoach.domain.repositories.SpeechToTextRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SpeechToTextRepositoryImpl(
    val apiService: ApiService
) : SpeechToTextRepository {

    override suspend fun transcribeAudio(audioBytes: ByteArray): TResult<SpeechToTextResultEntity, LoadingException> =
        withContext(Dispatchers.IO) {
            return@withContext runCatching {

                val base64Audio = Base64.encodeToString(audioBytes, Base64.NO_WRAP)

                val request = base64Audio.toChatRequestForAudio()

                val response = apiService.transcribeAudio(request)

                val resultText = response.choices.firstOrNull()?.message?.content ?: ""

                TResult.Success<SpeechToTextResultEntity, LoadingException>(
                    data = SpeechToTextResultEntity(
                        resultText
                    )
                )

            }.getOrElse {
                TResult.Error(exception = it.parseToLoadingException())
            }
        }
}
