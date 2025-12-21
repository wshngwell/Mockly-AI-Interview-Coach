package com.example.interviewaicoach.domain.usecases.recordSpeechUseCases

import com.example.interviewaicoach.domain.repositories.SpeechRecognitionRepository

class DestroyRecordingVoiceUseCase(
    private val speechRecognitionRepository: SpeechRecognitionRepository
) {
    operator fun invoke() = speechRecognitionRepository.release()
}