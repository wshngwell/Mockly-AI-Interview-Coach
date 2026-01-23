package com.example.interviewaicoach.domain.repositories

import com.example.interviewaicoach.domain.entities.LoadingException
import com.example.interviewaicoach.domain.entities.TResult
import com.example.interviewaicoach.domain.entities.speechToTextEntities.SpeechToTextResultEntity

interface SpeechToTextRepository {

    suspend fun transcribeAudio(audioBytes: ByteArray): TResult<SpeechToTextResultEntity, LoadingException>
}