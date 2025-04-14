package space.song.database.entities

import androidx.room.Entity
import androidx.room.Fts4

@Entity(tableName = "demoFts")
@Fts4
data class DemoEntity (
    val userId: Int,
    val title: String,
    val body: String,
)

