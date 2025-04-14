package space.song.data.sync

import android.util.Log
import kotlin.coroutines.cancellation.CancellationException

interface Synchronizer {
    suspend fun Syncable.sync() = this@sync.syncWith(this@Synchronizer)
}

interface Syncable {
    suspend fun syncWith(synchronizer: Synchronizer): Boolean
}


const val INPUT_DATA_SYNC_TYPES_KEY = "input_data_sync_types_key"

enum class SyncableType {
    DEMO,
    USER,
    POST,
    COMMENT,
    ALBUM,
    PHOTO,
    TODO,
    ALBUM_PHOTO,
    ALL
}

private suspend fun <T> suspendRunCatching(block: suspend () -> T): Result<T> = try {
    Result.success(block())
} catch (cancellationException: CancellationException) {
    throw cancellationException
} catch (exception: Exception) {
    Log.i(
        "suspendRunCatching",
        "Failed to evaluate a suspendRunCatchingBlock. Returning failure Result",
        exception,
    )
    Result.failure(exception)
}

/**
 *
 * 针对一般数据列表的同步操作
 */


suspend fun Synchronizer.genericChangeAllSync(
    updatePolicy: () -> Unit,
): Boolean {
    return suspendRunCatching {
        updatePolicy()
    }.isSuccess
}

/**
 * 比较源列表（source）和目标列表（target），找出需要新增、删除和更新的项，
 * 并通过相应的回调函数处理这些项。
 *
 * 提供了可选的时间戳比较策略：
 * 1. 新增项：存在于 source 但不存在于 target 中的项。
 * 2. 删除项：存在于 target 但不存在于 source 中的项。
 * 3. 更新项：
 *    - 如果提供了 timestampExtractor：在 source 和 target 中都存在（ID相同），且时间戳不同的项。
 *    - 如果 timestampExtractor 为 null：在 source 和 target 中都存在（ID相同）的所有项。
 *    回调将始终收到 source 中的项，因为它代表了最新的状态。
 *
 * @param T 列表中元素的类型。
 * @param ID 元素 ID 的类型，必须是可用作 Map Key 的类型 (e.g., String, Int, Long)。
 * @param TS 时间戳的类型，必须是可比较的 (e.g., Long, Instant, Date)。仅当提供 timestampExtractor 时使用。
 * @param source 源数据列表（通常来自网络或更新的数据源）。
 * @param target 目标数据列表（通常是当前的本地数据）。
 * @param idExtractor 用于从元素 T 中提取 ID 的 lambda 函数。
 * @param timestampExtractor （可选）用于从元素 T 中提取时间戳 TS? 的 lambda 函数。如果为 null，则不进行时间戳比较，所有共同项都视为更新。
 * @param onItemsToAdd 当检测到需要新增的项时调用的回调函数，参数是 source 中需要新增的项列表。
 * @param onItemsToDelete 当检测到需要删除的项时调用的回调函数，参数是 target 中需要删除的项列表。
 * @param onItemsToUpdate 当检测到需要更新的项时调用的回调函数，参数是 source 中对应 ID 的、更新后的项列表。
 */
suspend fun <T, ID, TS : Comparable<TS>> Synchronizer.genericChangeListSync(
    totalSourceFetcher: suspend () -> List<T>, //获取全量数据
    localRemoteFetcher: suspend () -> List<T>, //获取本地数据
    idExtractor: (T) -> ID,
    timestampExtractor: ((T) -> TS?)? = null, // 使 lambda 本身可空，并提供默认值 null
    onItemsToAdd: suspend (List<T>) -> Unit,
    onItemsToDelete: suspend (List<T>) -> Unit,
    onItemsToUpdate: suspend (List<T>) -> Unit
): Boolean = suspendRunCatching{
    val source = totalSourceFetcher()
    val target = localRemoteFetcher()
    // 1. 创建 Map 以便高效查找 O(source.size + target.size)
    val sourceMap = source.associateBy(idExtractor)
    val targetMap = target.associateBy(idExtractor)

    // 2. 找出需要新增的项 (存在于 source 但不存在于 target) O(source.size)
    val itemsToAdd = source.filter { idExtractor(it) !in targetMap }
    if (itemsToAdd.isNotEmpty()) {
        println("[策略] 检测到 ${itemsToAdd.size} 个新增项: ${itemsToAdd.map(idExtractor)}")
        onItemsToAdd(itemsToAdd)
    }

    // 3. 找出需要删除的项 (存在于 target 但不存在于 source) O(target.size)
    val itemsToDelete = target.filter { idExtractor(it) !in sourceMap }
    if (itemsToDelete.isNotEmpty()) {
        println("[策略] 检测到 ${itemsToDelete.size} 个删除项: ${itemsToDelete.map(idExtractor)}")
        onItemsToDelete(itemsToDelete)
    }

    // 4. 找出需要更新的项 O(min(source.size, target.size))
    val itemsToUpdate = mutableListOf<T>()
    // 获取共同存在的 ID 集合
    val commonIds = sourceMap.keys.intersect(targetMap.keys)

    for (id in commonIds) {
        // 对于共同存在的 ID，获取 source 中的项（这是我们要用来更新的数据）
        val sourceItem = sourceMap.getValue(id)

        // --- 策略判断 ---
        if (timestampExtractor == null) {
            // 策略：未提供时间戳提取器，则所有共同项都视为需要更新
            itemsToUpdate.add(sourceItem)
        } else {
            // 策略：提供了时间戳提取器，进行比较
            val targetItem = targetMap.getValue(id) // 获取 target 中的项以提取其时间戳
            val sourceTimestamp = timestampExtractor(sourceItem)
            val targetTimestamp = timestampExtractor(targetItem)
            if (sourceTimestamp != targetTimestamp) {
                // 时间戳不同，视为需要更新
                itemsToUpdate.add(sourceItem)
            }
            // 如果时间戳相同，则不添加到 itemsToUpdate 列表
        }
        // --- 策略判断结束 ---
    }

    if (itemsToUpdate.isNotEmpty()) {
        val reason = if (timestampExtractor == null) "所有共同项" else "基于时间戳比较"
        println("[策略] 检测到 ${itemsToUpdate.size} 个更新项 ($reason): ${itemsToUpdate.map(idExtractor)}")
        onItemsToUpdate(itemsToUpdate)
    }

    if (itemsToAdd.isEmpty() && itemsToDelete.isEmpty() && itemsToUpdate.isEmpty()) {
        println("[策略] 数据一致，无需操作。")
    }
}.isSuccess


/**
 * 这是一个通用的同步方法，适用于大多数数据列表的同步操作
 * 但是需要有相应的服务器接口支持
 */
//suspend fun Synchronizer.changeListSync(
//    versionReader: (ChangeListVersions) -> Int,
//    changeListFetcher: suspend (Int) -> List<NetworkChangeList>,
//    versionUpdater: ChangeListVersions.(Int) -> ChangeListVersions,
//    modelDeleter: suspend (List<String>) -> Unit,
//    modelUpdater: suspend (List<String>) -> Unit,
//) = suspendRunCatching {
//    // Fetch the change list since last sync (akin to a git fetch)
//    val currentVersion = versionReader(getChangeListVersions())
//    val changeList = changeListFetcher(currentVersion)
//    if (changeList.isEmpty()) return@suspendRunCatching true
//
//    val (deleted, updated) = changeList.partition(NetworkChangeList::isDelete)
//
//    // Delete models that have been deleted server-side
//    modelDeleter(deleted.map(NetworkChangeList::id))
//
//    // Using the change list, pull down and save the changes (akin to a git pull)
//    modelUpdater(updated.map(NetworkChangeList::id))
//
//    // Update the last synced version (akin to updating local git HEAD)
//    val latestVersion = changeList.last().changeListVersion
//    updateChangeListVersions {
//        versionUpdater(latestVersion)
//    }
//}.isSuccess
