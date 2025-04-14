package space.song.data.repository

import kotlinx.coroutines.flow.Flow
import space.song.data.sync.Syncable
import space.song.model.demo.DemoModel

interface DemoNewsRepository : Syncable {
    fun getAllNews(): Flow<List<DemoModel>>
    fun getNewsById(userId: Int): Flow<DemoModel>
}
