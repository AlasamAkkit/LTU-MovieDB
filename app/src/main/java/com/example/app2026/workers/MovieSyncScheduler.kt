package com.example.app2026.workers

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.app2026.models.MovieListType

object MovieSyncScheduler {

    fun enqueueSync(
        context: Context,
        viewType: MovieListType
    ) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = OneTimeWorkRequestBuilder<MovieSyncWorker>()
            .setConstraints(constraints)
            .setInputData(
                workDataOf(
                    MovieSyncWorker.KEY_VIEW_TYPE to viewType.dbValue
                )
            )
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            MovieSyncWorker.UNIQUE_WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            request
        )
    }
}