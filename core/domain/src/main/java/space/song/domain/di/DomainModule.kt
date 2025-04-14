package space.song.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import space.song.domain.demo.DemoDomainManager
import space.song.domain.demo.DemoDomainManagerImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    @Singleton
    internal abstract fun bindDemoDomainManager(
        demoDomainManagerImpl: DemoDomainManagerImpl
    ): DemoDomainManager
}
