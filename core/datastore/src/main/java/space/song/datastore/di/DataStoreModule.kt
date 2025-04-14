package space.song.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import space.song.common.network.di.ApplicationScope
import space.song.common.network.di.Dispatcher
import space.song.common.network.di.RtwDispatchers
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import space.song.datastore.proto.AppPreferences
import space.song.datastore.serializer.AppPreferencesSerializer
import javax.inject.Singleton

// 定义 DataStore 文件名
private const val PB_APP_PREFERENCES = "app_preferences.pb"


@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    // Add your DataStore related bindings here

    @Provides
    @Singleton
    internal fun provideUserPreferencesDataStore(
        @Dispatcher(RtwDispatchers.IO) ioDispatcher: CoroutineDispatcher,
        @ApplicationContext context: Context,
        @ApplicationScope scope: CoroutineScope,
        appPreferencesSerializer: AppPreferencesSerializer
    ) : DataStore<AppPreferences> =
        DataStoreFactory.create(
            serializer = appPreferencesSerializer,
            scope = CoroutineScope(scope.coroutineContext + ioDispatcher)
        ) {
            context.dataStoreFile(PB_APP_PREFERENCES)
        }
}
