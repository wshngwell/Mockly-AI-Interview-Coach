package com.example.interviewaicoach.utils

import android.util.Log
import com.example.interviewaicoach.BuildConfig

fun myLog(msg: String) {
    if (BuildConfig.DEBUG) {
        runCatching {
            Log.d("!!!", msg)
        }.getOrElse {
            println(msg)
        }
    }
}