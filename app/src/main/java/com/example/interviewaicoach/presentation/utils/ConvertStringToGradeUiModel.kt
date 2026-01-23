package com.example.interviewaicoach.presentation.utils

import com.example.interviewaicoach.presentation.screens.chooseGradeScreen.GradeUIModel

fun GradeUIModel.convertToString() = when (this) {
    GradeUIModel.JUNIOR -> "Junior"
    GradeUIModel.MIDDLE -> "Middle"
    GradeUIModel.SENIOR -> "Senior"
    GradeUIModel.LEAD -> "Lead"
}