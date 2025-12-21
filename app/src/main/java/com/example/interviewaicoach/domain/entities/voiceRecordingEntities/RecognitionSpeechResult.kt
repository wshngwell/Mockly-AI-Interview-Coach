package com.example.interviewaicoach.domain.entities.voiceRecordingEntities

sealed class RecognitionSpeechResult {
    data class Success(val text: String) : RecognitionSpeechResult()
    data class Error(val message: String) : RecognitionSpeechResult()
    object Loading : RecognitionSpeechResult()
    object Idle : RecognitionSpeechResult()
}