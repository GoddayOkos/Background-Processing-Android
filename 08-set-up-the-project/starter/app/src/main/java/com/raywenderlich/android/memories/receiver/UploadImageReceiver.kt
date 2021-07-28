package com.raywenderlich.android.memories.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

const val ACTION_IMAGE_UPLOAD = "image_upload"

class UploadImageReceiver(
    private inline val onImageUpload: (Boolean) -> Unit
) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == ACTION_IMAGE_UPLOAD) {
            val isUploaded = intent.getBooleanExtra("is_uploaded", false)

            onImageUpload(isUploaded)
        }
    }
}