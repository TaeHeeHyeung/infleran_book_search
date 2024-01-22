package com.qualitybitz.booksearchapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.project.infleran_booksearchapp.data.repository.BookSearchRepository
import com.project.infleran_booksearchapp.worker.CacheDeleteWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val bookSearchRepository: BookSearchRepository,
    private val workManager: WorkManager
) : ViewModel() {

    // WorkManager
    fun setWork() {
        val constraints = Constraints.Builder()
//            .setRequiresCharging(true) // 충전 중일 떄 만 실행
            .setRequiresBatteryNotLow(true)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<CacheDeleteWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniquePeriodicWork(
            WORKER_KEY, ExistingPeriodicWorkPolicy.REPLACE, workRequest
        )
    }


    // DataStore
    fun saveSortMode(value: String) = viewModelScope.launch(Dispatchers.IO) {
        bookSearchRepository.saveSortMode(value)
    }

    fun saveCacheDeleteMode(value: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        bookSearchRepository.saveCacheDeleteMode(value)
    }

    suspend fun getCacheDeleteMode() = withContext(Dispatchers.IO) {
        bookSearchRepository.getCacheDeleteMode().first()
    }

    suspend fun getSortMode() = withContext(Dispatchers.IO) {
        bookSearchRepository.getSortMode().first()
    }

    fun deleteWork() = workManager.cancelUniqueWork(WORKER_KEY)

    fun getWorkStatus(): LiveData<MutableList<WorkInfo>> =
        workManager.getWorkInfosForUniqueWorkLiveData(WORKER_KEY)

    companion object {
        private const val WORKER_KEY = "cache_worker"
    }

}