package com.example.interviewaicoach.presentation

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.example.interviewaicoach.R
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker

class PermissionLauncher(
    private val permission: String = "",
    private val onSuccess: () -> Unit = {},
    private val launcher: ManagedActivityResultLauncher<String, Boolean>,
    private val context: Context
) {
    fun launch() {
            if (ContextCompat.checkSelfPermission(
                    context,
                    permission
                ) == PermissionChecker.PERMISSION_GRANTED
            ) {
                onSuccess()
            } else {
                launcher.launch(permission)
            }
    }

    companion object {

        const val RECORD_AUDIO = Manifest.permission.RECORD_AUDIO

        @Composable
        fun build(
            permissionName: String,
            onSuccess: () -> Unit,
            onFailure: () -> Unit
        ): PermissionLauncher {
            val context = LocalContext.current
            val shouldShowDialog = remember { mutableStateOf(false) }

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission(),
                onResult = { isGranted ->
                    if (isGranted) {
                        onSuccess()
                    } else {
                        shouldShowDialog.value = true
                        onFailure()
                    }
                }
            )
            if (shouldShowDialog.value) {
                PermissionDialog(
                    onDismiss = { shouldShowDialog.value = false },
                    onConfirm = {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", context.packageName, null)
                        }
                        context.startActivity(intent)
                        shouldShowDialog.value = false
                    },
                    titleText = stringResource(R.string.mic_permission_title),
                    descriptionText = stringResource(R.string.mic_permission_descr),
                    confirmButtonText = stringResource(R.string.confirm_permission),
                    dismissButtonText = stringResource(R.string.not_now),
                )
            }
            return remember {
                PermissionLauncher(permissionName, onSuccess, launcher, context)
            }
        }
    }

}