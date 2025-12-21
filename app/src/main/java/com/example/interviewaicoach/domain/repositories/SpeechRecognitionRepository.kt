package com.example.interviewaicoach.domain.repositories

import android.app.Application
import com.example.interviewaicoach.domain.entities.voiceRecordingEntities.RecognitionSpeechResult

interface SpeechRecognitionRepository {
    suspend fun initializeModel(application: Application): Boolean
    suspend fun recognizeSpeech(): RecognitionSpeechResult
    fun stopRecording()
    fun release()
}