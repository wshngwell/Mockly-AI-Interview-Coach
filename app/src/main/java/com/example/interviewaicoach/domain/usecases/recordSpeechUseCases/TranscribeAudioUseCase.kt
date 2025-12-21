package com.example.interviewaicoach.domain.usecases.recordSpeechUseCases

import com.example.interviewaicoach.domain.repositories.SpeechToTextRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TranscribeAudioUseCase(
    private val repository: SpeechToTextRepository
) {
    suspend operator fun invoke(audio: ByteArray) = withContext(Dispatchers.IO) {
        repository.transcribeAudio(audio)
    }
}