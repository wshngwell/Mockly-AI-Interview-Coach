package com.example.interviewaicoach.domain.usecases.recordSpeechUseCases

import android.app.Application
import com.example.interviewaicoach.domain.repositories.SpeechRecognitionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InitializeRecordingVoiceUseCase(
    private val speechRecognitionRepository: SpeechRecognitionRepository
) {
    suspend operator fun invoke(application: Application) = withContext(Dispatchers.IO) {
        speechRecognitionRepository.initializeModel(application)
    }

}