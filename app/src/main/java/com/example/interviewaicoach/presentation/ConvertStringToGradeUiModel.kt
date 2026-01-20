package com.example.interviewaicoach.presentation

fun GradeUIModel.convertToString() = when (this) {
    GradeUIModel.JUNIOR -> "Junior"
    GradeUIModel.MIDDLE -> "Middle"
    GradeUIModel.SENIOR -> "Senior"
    GradeUIModel.LEAD -> "Lead"
}