package space.song.network.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import space.song.network.externalDataSource.DemoPostInterface
import space.song.network.externalDataSource.impl.DemoPostImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    @Binds
    @Singleton
    abstract fun bindDemoPostCall(demoPostImpl: DemoPostImpl): DemoPostInterface

}
