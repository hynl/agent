package space.song.database

import androidx.room.Database
import androidx.room.RoomDatabase
import space.song.database.dao.DemoDao
import space.song.database.entities.DemoEntity

@Database (
    entities = [DemoEntity::class],
    version = 1,
    autoMigrations = []
)
internal abstract class RtwDatabase: RoomDatabase() {
    abstract fun demoDao(): DemoDao
}
