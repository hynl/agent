package space.song.sync.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import space.song.common.network.di.Dispatcher
import space.song.common.network.di.RtwDispatchers
import space.song.data.repository.DemoNewsRepository
import space.song.data.sync.Synchronizer
import space.song.sync.helper.SyncConstraints
import space.song.sync.helper.syncForegroundInfo

@HiltWorker
internal class AppSyncWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val demoRepository: DemoNewsRepository,
    @Dispatcher(RtwDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : CoroutineWorker(appContext, workerParams), Synchronizer {

    override suspend fun getForegroundInfo(): ForegroundInfo =
        appContext.syncForegroundInfo()


    override suspend fun doWork(): Result = withContext(ioDispatcher) {
        val syncResult = awaitAll(
            async { demoRepository.sync() },
        ).all { it }

        if (syncResult) {
            // Notify the sync status
            Result.success()
        } else {
            Result.retry()
        }
    }

    companion object {
        fun startUpSyncWork() = OneTimeWorkRequestBuilder<DelegatingWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setConstraints(SyncConstraints)
            .setInputData(AppSyncWorker::class.delegatedData())
            .build()
    }
}
