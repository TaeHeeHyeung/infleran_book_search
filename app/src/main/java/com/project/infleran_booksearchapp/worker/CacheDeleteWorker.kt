package com.project.infleran_booksearchapp.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class CacheDeleteWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workParams: WorkerParameters,
    private val cacheDeleteResult: String
) : Worker(context, workParams) {

    override fun doWork(): Result {
        return try {
//            Log.d("WorkManager", "Cache has successfully delete")
            Log.d("workManager", cacheDeleteResult)
            Result.success()
        } catch (exception: Exception) {
            exception.printStackTrace();
            Result.failure()
        }
    }
}