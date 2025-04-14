package space.song.data.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import space.song.data.repository.DemoNewsRepository
import space.song.data.sync.Synchronizer
import space.song.data.sync.genericChangeListSync
import space.song.database.dao.DemoDao
import space.song.database.entities.DemoEntity
import space.song.model.demo.DemoModel
import space.song.network.externalDataSource.DemoPostInterface
import space.song.network.model.ResponseResource
import javax.inject.Inject

internal class DemoNewsRepositoryImpl @Inject constructor(
    private val demoDao: DemoDao,
    private val network: DemoPostInterface
) : DemoNewsRepository {
    override fun getAllNews(): Flow<List<DemoModel>> =
        demoDao.getAllDemoEntities().map {
            it.map { entity ->
                DemoModel(
                    title = entity.title,
                    body = entity.body
                )
            }
        }

    override fun getNewsById(userId: Int): Flow<DemoModel> =
        demoDao.getDemoEntityByUserId(userId).map {
            DemoModel(
                title = it.title,
                body = it.body
            )
        }

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean =
        synchronizer.genericChangeListSync(
            totalSourceFetcher = {
                when (val postResponse = network.getAllPosts()) {
                    is ResponseResource.Success -> {
                        postResponse.data.map { response ->
                            DemoEntity(
                                userId = response.userId,
                                title = response.title,
                                body = response.body
                            )
                        }
                    }

                    is ResponseResource.Failure -> {
                        throw Exception("Request failed")
                    }
                }
            }, //获取全量数据
            localRemoteFetcher = {
                demoDao.getOnceAllDemoEntities().map { entity ->
                    DemoEntity(
                        userId = entity.userId,
                        title = entity.title,
                        body = entity.body
                    )
                }
            }, //获取本地数据
            idExtractor = {
                it.userId
            },
            timestampExtractor = {
                null
            }, // 使 lambda 本身可空，并提供默认值 null
            onItemsToAdd = { entities ->
                demoDao.upsertDemoEntities(entities)
            },
            onItemsToDelete = { entities ->
                demoDao.deleteDemoEntities(entities.map { it.userId })
            },
            onItemsToUpdate = { entities ->
                demoDao.upsertDemoEntities(entities)

            }
        )
}
