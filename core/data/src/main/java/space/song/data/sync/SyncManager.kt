package space.song.data.sync

import kotlinx.coroutines.flow.Flow

/**
 * 适用于
 * 1.离线优先
 * 2.数据库/本地存储同步
 * 3.大数据量数据
 */
interface SyncManager {
    val isAppSyncing: Flow<Boolean>
    val isTaskSyncing: Flow<Boolean>
    fun requestAppSync()
    suspend fun requestSync(dataTypes: List<SyncableType> = listOf(SyncableType.ALL))
}
