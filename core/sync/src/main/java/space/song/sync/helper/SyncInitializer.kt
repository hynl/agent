package space.song.sync.helper

import android.content.Context
import androidx.work.WorkManager
import space.song.sync.workers.AppSyncWorker

internal const val SYNC_WORK_NAME = "SyncAppWorkForRTW"
internal const val SYNC_TASK_NAME = "SyncTaskWorkForRTW"

/**
 * 项目初始化同步
 */
object SyncInitializer {
    fun init(context: Context) {
        WorkManager.getInstance(context).apply {
            enqueueUniqueWork(
                SYNC_WORK_NAME,
                androidx.work.ExistingWorkPolicy.KEEP,
                AppSyncWorker.startUpSyncWork(),
            )
        }
    }
}


