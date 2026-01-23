package com.example.interviewaicoach.presentation.screens.questionsWithAnswersScreen

fun formatRecordingTime(ms: Long): String {
    val hundredths = (ms % 1000) / 10
    val seconds = (ms / 1000) % 60
    val minutes = (ms / (1000 * 60)) % 60
    return String.format( "%d:%02d,%02d", minutes, seconds, hundredths)
}