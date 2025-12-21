package com.example.interviewaicoach.data.local.reposiroties

import android.app.Application
import com.example.interviewaicoach.data.local.voskService.VoskModelManager
import com.example.interviewaicoach.data.local.voskService.VoskRecognitionService
import com.example.interviewaicoach.domain.entities.voiceRecordingEntities.RecognitionSpeechResult
import com.example.interviewaicoach.domain.repositories.SpeechRecognitionRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.toList

class SpeechRecognitionRepositoryImpl(
    private val application: Application
) : SpeechRecognitionRepository {

    private val modelManager = VoskModelManager(application)
    private val voskService = VoskRecognitionService()

    override suspend fun initializeModel(application: Application): Boolean {
        return runCatching {
            val modelPath = modelManager.getModelPath()
            voskService.initialize(modelPath)
            true
        }.getOrElse {
            it.printStackTrace()
            false
        }
    }

    override suspend fun recognizeSpeech(): RecognitionSpeechResult {
        return runCatching {

            val results = voskService.recognizeFlow()
                .catch { /* можно логировать e */ }
                .toList()

            val finalText = results.lastOrNull { it.isNotEmpty() }?.trim()

            if (finalText != null) {
                RecognitionSpeechResult.Success(finalText)
            } else {
                RecognitionSpeechResult.Error("Речь не распознана")
            }
        }.getOrElse {
            RecognitionSpeechResult.Error(it.message ?: "Не удалось распознать речь")
        }
    }

    override fun stopRecording() {
        voskService.stopRecording()
    }

    override fun release() {
        voskService.release()
    }
}