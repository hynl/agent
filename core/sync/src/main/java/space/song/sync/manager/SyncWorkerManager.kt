package space.song.sync.manager

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkInfo
import androidx.work.WorkInfo.State
import androidx.work.WorkManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.map
import space.song.common.network.di.ApplicationScope
import space.song.data.sync.SyncManager
import space.song.data.sync.SyncableType
import space.song.sync.helper.SYNC_TASK_NAME
import space.song.sync.helper.SYNC_WORK_NAME
import space.song.sync.workers.AppSyncWorker
import space.song.sync.workers.SpecializedSyncWorker
import javax.inject.Inject

/**
 * 主动或其他任何需要同步的场景
 */
internal class SyncWorkerManager @Inject constructor(
    @ApplicationScope private val context: Context
) : SyncManager {
    override val isAppSyncing: Flow<Boolean>
        get() = WorkManager.getInstance(context).getWorkInfosForUniqueWorkFlow(SYNC_WORK_NAME)
            .map(List<WorkInfo>::anyRunning)
            .conflate()

    override val isTaskSyncing: Flow<Boolean>
        get() = WorkManager.getInstance(context).getWorkInfosForUniqueWorkFlow(SYNC_TASK_NAME)
            .map(List<WorkInfo>::anyRunning)
            .conflate()

    override fun requestAppSync() {
        val workManager = WorkManager.getInstance(context)
        // Run sync on app startup and ensure only one sync worker runs at any time
        workManager.enqueueUniqueWork(
            SYNC_WORK_NAME,
            ExistingWorkPolicy.KEEP,
            AppSyncWorker.startUpSyncWork(),
        )
    }

    override suspend fun requestSync(dataTypes: List<SyncableType>){
        val workManager = WorkManager.getInstance(context)
        // Run sync on app startup and ensure only one sync worker runs at any time
        workManager.enqueueUniqueWork(
            SYNC_TASK_NAME,
            ExistingWorkPolicy.APPEND,
            SpecializedSyncWorker.startUpSyncWork(dataTypes),
        )
    }
}

private fun List<WorkInfo>.anyRunning() = any { it.state == State.RUNNING || it.state == State.ENQUEUED }