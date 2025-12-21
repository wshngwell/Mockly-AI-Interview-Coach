package com.example.interviewaicoach.domain.usecases.recordSpeechUseCases

import com.example.interviewaicoach.domain.repositories.RecordAudioRepository

class StartRecordingUseCase(
    private val recordAudioRepository: RecordAudioRepository
) {
    suspend operator fun invoke() = recordAudioRepository.start()
}