package com.example.interviewaicoach.domain.usecases.recordSpeechUseCases

import com.example.interviewaicoach.domain.repositories.SpeechRecognitionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StartRecodingVoiceUseCase(
    private val speechRecognitionRepository: SpeechRecognitionRepository
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        speechRecognitionRepository.recognizeSpeech()
    }
}