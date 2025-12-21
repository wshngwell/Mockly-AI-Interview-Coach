package com.example.interviewaicoach.domain.entities

sealed class LoadingException(ex: Throwable) : Throwable(ex) {

    override val cause: Throwable = ex

    class NetworkError(ex: Throwable) : LoadingException(ex)

    class HttpError(ex: Throwable) : LoadingException(ex)

    class EmptyAnswer(ex: Throwable) : LoadingException(ex)

    class OtherError(ex: Throwable) : LoadingException(ex)
}