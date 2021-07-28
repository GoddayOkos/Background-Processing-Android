package com.raywenderlich.android.memories.service

import android.app.IntentService
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.raywenderlich.android.memories.utils.FileUtils
import com.raywenderlich.android.memories.utils.toast
import java.io.File

const val SERVICE_NAME = "Download image service"

class DownloadService : IntentService(SERVICE_NAME) {

    override fun onHandleIntent(intent: Intent?) {
        val imagePath = intent?.getStringExtra("image_path")

        if (imagePath != null) {
            downloadImage(imagePath)
        } else {
            Log.d("Missing image path", "Stopping service")
            stopSelf()
        }
    }

    private fun downloadImage(imagePath: String) {
            val file = File(applicationContext.externalMediaDirs.first(), imagePath)
            FileUtils.downloadImage(file, imagePath)
    }

    override fun onDestroy() {
        applicationContext?.toast("Stopping service!")
        super.onDestroy()
    }
}