package com.raywenderlich.android.memories.service

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import com.raywenderlich.android.memories.App
import com.raywenderlich.android.memories.model.result.Success
import com.raywenderlich.android.memories.utils.FileUtils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SynchronizedImageService : JobIntentService() {

    private val remoteApi by lazy { App.remoteApi }

    companion object {
        private const val JOB_ID = 15

        fun startWork(context: Context, intent: Intent) {
            enqueueWork(context, SynchronizedImageService::class.java, JOB_ID, intent)
        }
    }

    override fun onHandleWork(intent: Intent) {
        clearStorage()
        fetchImages()
    }

    private fun fetchImages() {
        GlobalScope.launch {
            val result = remoteApi.getImages()

            if (result is Success) {
                val imageArray = result.data.map { it.imagePath }.toTypedArray()

                FileUtils.queueImagesForDownload(applicationContext, imageArray)
            }
        }
    }

    private fun clearStorage() {
        FileUtils.clearLocalStorage(applicationContext)
    }


}