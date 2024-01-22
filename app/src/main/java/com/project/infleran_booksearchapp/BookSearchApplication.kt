package com.project.infleran_booksearchapp

import android.app.Application
import androidx.work.Configuration
import androidx.hilt.work.HiltWorkerFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class BookSearchApplication : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

//    override fun getWorkManagerConfiguration(): Configuration {
//        return Configuration.Builder()
//            .setWorkerFactory(workerFactory)
//            .build()
//    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()


}