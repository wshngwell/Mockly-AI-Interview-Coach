package com.example.interviewaicoach.domain.usecases.recordSpeechUseCases

import com.example.interviewaicoach.domain.repositories.SpeechRecognitionRepository

class StopRecodingVoiceUseCase(
    private val speechRecognitionRepository: SpeechRecognitionRepository
) {
    operator fun invoke() = speechRecognitionRepository.stopRecording()
}