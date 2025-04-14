package space.song.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import space.song.database.entities.DemoEntity

@Dao
interface DemoDao {
    // Define your DAO methods here
    // For example:
    @Insert
    suspend fun insertDemoEntity(demoEntity: DemoEntity)

    @Query(
        value = """
            SELECT * FROM demoFts
        """,
    )
    fun getAllDemoEntities(): Flow<List<DemoEntity>>

    @Query(
        value = """
            SELECT * FROM demoFts
        """,
    )
    suspend fun getOnceAllDemoEntities(): List<DemoEntity>

    @Query("SELECT * FROM demoFts WHERE userId = :userId")
    suspend fun getOnceDemoEntityByUserId(userId: Int): List<DemoEntity>

    @Query("SELECT * FROM demoFts WHERE userId = :userId")
    fun getDemoEntityByUserId(userId: Int): Flow<DemoEntity>

    @Query(
        value = """
            DELETE FROM demoFts
            WHERE userId in (:userIds)
        """,
    )
    suspend fun deleteDemoEntities(userIds: List<Int>)

    @Upsert
    suspend fun upsertDemoEntities(demoEntities: List<DemoEntity>)
}
