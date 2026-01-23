package com.example.interviewaicoach.domain.entities

sealed class TResult<T, V>(
    open val data: T? = null,
    open val exception: V? = null
) {
    data class Success<T, V>(override val data: T) : TResult<T, V>()
    data class Error<T, V>(override val exception: V) : TResult<T, V>()
}
