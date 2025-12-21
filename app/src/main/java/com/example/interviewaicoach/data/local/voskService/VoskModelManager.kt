package com.example.interviewaicoach.data.local.voskService

import android.app.Application
import java.io.File

class VoskModelManager(
    private val application: Application
) {


    fun getModelPath(): String {
        val modelDir = File(application.filesDir, MODEL_NAME)

        if (!modelDir.exists()) {
            copyModelFromAssets(modelDir)
        }

        return modelDir.absolutePath
    }

    private fun copyModelFromAssets(modelDir: File) {
        modelDir.mkdirs()
        val assetManager = application.assets
        val files = assetManager.list(MODEL_NAME) ?: return

        files.forEach { fileName ->
            assetManager.open("$MODEL_NAME/$fileName").use { input ->
                File(modelDir, fileName).outputStream().use { output ->
                    input.copyTo(output)
                }
            }
        }
    }

    companion object {
        private const val MODEL_NAME = "vosk-model-small-ru-0.22"
    }
}