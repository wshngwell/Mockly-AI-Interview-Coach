package com.example.interviewaicoach.data.repositories

import android.annotation.SuppressLint
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import androidx.lifecycle.AtomicReference
import com.example.interviewaicoach.domain.entities.LoadingException
import com.example.interviewaicoach.domain.entities.TResult
import com.example.interviewaicoach.domain.repositories.RecordAudioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.concurrent.atomic.AtomicBoolean

class RecordAudioRepositoryImpl : RecordAudioRepository {
    private var audioRecord: AudioRecord? = null
    private val isRecording = AtomicBoolean(false)

    private val recordingMutex = Mutex()

    private val sampleRate = 16000
    private val channelConfig = AudioFormat.CHANNEL_IN_MONO
    private val audioFormat = AudioFormat.ENCODING_PCM_16BIT
    private val minBufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat)

    @SuppressLint("MissingPermission")
    override suspend fun start(): TResult<ByteArray, LoadingException> =
        withContext(Dispatchers.IO) {

            if (isRecording.get()) return@withContext TResult.Error(LoadingException.SpeechRecordingError())

            recordingMutex.withLock {

                if (audioRecord != null) {
                    releaseResources()
                    delay(100)
                }

                return@withContext try {
                    audioRecord = AudioRecord(
                        MediaRecorder.AudioSource.MIC,
                        sampleRate, channelConfig, audioFormat, minBufferSize
                    ).apply {
                        if (state != AudioRecord.STATE_INITIALIZED) {
                            throw Exception("AudioRecord initialization failed")
                        }
                        startRecording()
                    }

                    isRecording.set(true)
                    val outputStream = ByteArrayOutputStream()
                    val buffer = ByteArray(minBufferSize)


                    while (isActive && isRecording.get()) {
                        val currentRecord = audioRecord
                        val readResult = currentRecord?.read(buffer, 0, buffer.size) ?: -1
                        if (readResult > 0) {
                            outputStream.write(buffer, 0, readResult)
                        } else if (readResult < 0) {
                            break
                        }
                    }

                    val pcmData = outputStream.toByteArray()
                    if (pcmData.isNotEmpty()) {
                        TResult.Success(addWavHeader(pcmData))
                    } else {
                        TResult.Error(LoadingException.SpeechRecordingError())
                    }
                } catch (e: Exception) {
                    TResult.Error(LoadingException.SpeechRecordingError())
                } finally {
                    releaseResources()
                }
            }
        }

    override fun stop() {
        isRecording.set(false)
    }

    private fun releaseResources() {
        audioRecord?.apply {
            try {
                if (recordingState == AudioRecord.RECORDSTATE_RECORDING) {
                    stop()
                }
                release()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        audioRecord = null
    }

    private fun addWavHeader(pcmData: ByteArray): ByteArray {
        val header = ByteBuffer.allocate(44).apply {
            order(ByteOrder.LITTLE_ENDIAN)
            put("RIFF".toByteArray())
            putInt(36 + pcmData.size)
            put("WAVE".toByteArray())
            put("fmt ".toByteArray())
            putInt(16)
            putShort(1.toShort())
            putShort(1.toShort())
            putInt(sampleRate)
            putInt(sampleRate * 2)
            putShort(2.toShort())
            putShort(16.toShort())
            put("data".toByteArray())
            putInt(pcmData.size)
        }
        return header.array() + pcmData
    }
}