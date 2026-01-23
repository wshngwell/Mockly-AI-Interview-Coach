package com.example.interviewaicoach.domain.usecases.recordSpeechUseCases

import com.example.interviewaicoach.domain.repositories.RecordAudioRepository

class StopRecordingUseCase(
    private val recordAudioRepository: RecordAudioRepository
) {
    operator fun invoke() = recordAudioRepository.stop()
}