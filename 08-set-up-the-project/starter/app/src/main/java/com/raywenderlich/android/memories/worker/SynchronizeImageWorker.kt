package com.raywenderlich.android.memories.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.raywenderlich.android.memories.utils.FileUtils
import java.io.File

class SynchronizeImageWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters){

    override fun doWork(): Result {
        val images = inputData.getStringArray("images") ?: return Result.failure()

        images.forEach { imagePath ->
            val file = File(applicationContext.externalMediaDirs.first(), imagePath)
            FileUtils.downloadImage(file, imagePath)
        }
        return Result.success()
    }
}