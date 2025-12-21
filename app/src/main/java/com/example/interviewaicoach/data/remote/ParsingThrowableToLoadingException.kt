package com.example.interviewaicoach.data.remote

import com.example.interviewaicoach.domain.entities.LoadingException
import com.example.interviewaicoach.utils.myLog
import retrofit2.HttpException
import java.io.IOException

fun Throwable.parseToLoadingException(): LoadingException {

    myLog(this.stackTraceToString())

    return when (this) {
        is HttpException -> LoadingException.HttpError(this)

        is IOException -> LoadingException.NetworkError(this)

        is LoadingException -> this

        else -> LoadingException.OtherError(this)
    }
}