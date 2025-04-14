package space.song.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import space.song.database.RtwDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun providesNiaDatabase(
        @ApplicationContext context: Context,
    ): RtwDatabase = Room.databaseBuilder(
        context,
        RtwDatabase::class.java,
        "rtw-database",
    ).build()
}

@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {

    @Provides
    fun providesDemoDao(
        database: RtwDatabase,
    ) = database.demoDao()
}
