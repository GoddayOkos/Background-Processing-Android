package com.raywenderlich.android.memories.service

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import com.raywenderlich.android.memories.App
import com.raywenderlich.android.memories.receiver.ACTION_IMAGE_UPLOAD
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File


class UploadService : JobIntentService() {

    private val remoteApi by lazy { App.remoteApi }

    companion object {
        private const val JOB_ID = 52

        fun startWork(context: Context, intent: Intent) {
            enqueueWork(context, UploadService::class.java, JOB_ID, intent)
        }
    }

    override fun onHandleWork(intent: Intent) {
        val filePath = intent.getStringExtra("image_path")
        if (filePath != null) {
            uploadImage(filePath)
        }
    }

    private fun uploadImage(filePath: String) {
        GlobalScope.launch {
            val result = remoteApi.uploadImage(File(filePath))

            val intent = Intent().also {
                it.putExtra("is_uploaded", result.message == "Success")
                it.action = ACTION_IMAGE_UPLOAD
            }

            sendBroadcast(intent)
        }
    }


}