package com.example.interviewaicoach.domain.entities

sealed class LoadingException : Throwable() {


    class NetworkError : LoadingException()

    class HttpError : LoadingException()

    class SpeechRecordingError : LoadingException()

    class OtherError : LoadingException()
}