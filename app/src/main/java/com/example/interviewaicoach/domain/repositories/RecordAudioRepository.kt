package com.example.interviewaicoach.domain.repositories

import com.example.interviewaicoach.domain.entities.LoadingException
import com.example.interviewaicoach.domain.entities.TResult

interface RecordAudioRepository {
    suspend fun start(): TResult<ByteArray, LoadingException>
    fun stop()
}