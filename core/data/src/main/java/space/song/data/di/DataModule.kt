package space.song.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import space.song.data.repository.AppPreferencesRepository
import space.song.data.repository.DemoNewsRepository
import space.song.data.repository.impl.AppPreferencesRepositoryImpl
import space.song.data.repository.impl.DemoNewsRepositoryImpl
import space.song.data.util.ConnectivityNetworkMonitor
import space.song.data.util.NetworkMonitor

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun bindsNetworkMonitor(
        networkMonitor: ConnectivityNetworkMonitor,
    ): NetworkMonitor

    @Binds
    internal abstract fun bindDemoNewsRepository(
        demoNewsRepository: DemoNewsRepositoryImpl,
    ): DemoNewsRepository

    @Binds
    internal abstract fun bindAppPreferencesRepository(
        appPreferencesRepository: AppPreferencesRepositoryImpl,
    ): AppPreferencesRepository
}
