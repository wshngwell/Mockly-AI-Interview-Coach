package com.example.interviewaicoach.data.local.voskService

import android.annotation.SuppressLint
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
import org.vosk.Model
import org.vosk.Recognizer

class VoskRecognitionService {

    private var model: Model? = null
    private var recognizer: Recognizer? = null
    private var audioRecord: AudioRecord? = null
    private var isRecording = false

    fun initialize(modelPath: String) {
        model = Model(modelPath)
        recognizer = Recognizer(model, 16000.0f)
    }

    fun recognizeFlow(): Flow<String> = callbackFlow {
        if (isRecording) {
            close()
            return@callbackFlow
        }
        isRecording = true

        val minBufferSize = AudioRecord.getMinBufferSize(
            16000,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT
        ) * 2

        @SuppressLint("MissingPermission")
        audioRecord = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            16000,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT,
            minBufferSize
        )

        withContext(Dispatchers.IO) {
            try {
                audioRecord?.startRecording()
                val buffer = ShortArray(4096)

                while (isRecording) {
                    val read = audioRecord?.read(buffer, 0, buffer.size) ?: 0
                    if (read > 0) {
                        val floatBuffer = FloatArray(read) { buffer[it] / 32768.0f }
                        if (recognizer?.acceptWaveForm(floatBuffer, read) == true) {
                            val partial = recognizer?.result ?: ""
                            val text = extractText(partial)
                            if (text.isNotEmpty()) trySend(text)
                        }
                    }
                }

                val finalResult = recognizer?.finalResult ?: ""
                val finalText = extractText(finalResult)
                if (finalText.isNotEmpty()) {
                    trySend(finalText)
                }

            } finally {
                audioRecord?.stop()
                audioRecord?.release()
                audioRecord = null
            }
        }

        awaitClose {
            stopRecording()
        }
    }

    fun stopRecording() {
        isRecording = false
    }

    fun release() {
        stopRecording()
        recognizer?.close()
        model?.close()
        model = null
        recognizer = null
    }

    private fun extractText(json: String): String {
        return """"text"\s*:\s*"([^"]*)"""".toRegex().find(json)?.groupValues?.get(1) ?: ""
    }
}