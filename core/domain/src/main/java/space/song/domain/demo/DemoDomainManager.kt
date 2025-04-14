package space.song.domain.demo

import kotlinx.coroutines.flow.Flow
import space.song.model.demo.DemoModel

interface DemoDomainManager {
    fun getAllPosts(): Flow<List<DemoModel>>
}
