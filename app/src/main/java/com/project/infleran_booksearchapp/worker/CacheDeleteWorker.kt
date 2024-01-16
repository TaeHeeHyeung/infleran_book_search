package com.project.infleran_booksearchapp.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class CacheDeleteWorker(
    context: Context,
    workParams: WorkerParameters
) : Worker(context, workParams) {

    override fun doWork(): Result {
        return try {
            Log.d("WorkManager", "Cache has successfully delete")
            Result.success()
        } catch (exception: Exception) {
            exception.printStackTrace();
            Result.failure()
        }
    }
}