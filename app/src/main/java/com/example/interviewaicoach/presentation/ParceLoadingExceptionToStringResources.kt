package com.example.interviewaicoach.presentation

import com.example.interviewaicoach.R
import com.example.interviewaicoach.domain.entities.LoadingException


fun LoadingException.parseLoadingExceptionToNameErrorStringResource() = when (this) {
    is LoadingException.HttpError -> R.string.generation_error
    is LoadingException.NetworkError -> R.string.network_error
    is LoadingException.OtherError -> R.string.generation_error
    is LoadingException.SpeechRecordingError -> R.string.speech_error
}

fun LoadingException.parseLoadingExceptionToDescriptionErrorStringResource() = when (this) {
    is LoadingException.HttpError -> R.string.generationError_desc
    is LoadingException.NetworkError -> R.string.networkError_desc
    is LoadingException.OtherError -> R.string.generationError_desc
    is LoadingException.SpeechRecordingError -> R.string.generationError_desc
}

fun LoadingException.parseLoadingExceptionToImageErrorResource() = when (this) {
    is LoadingException.HttpError-> R.drawable.generation_error
    is LoadingException.NetworkError -> R.drawable.network_error_image
    is LoadingException.OtherError -> R.drawable.generation_error
    is LoadingException.SpeechRecordingError -> R.drawable.generation_error
}