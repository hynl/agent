package space.song.data.repository.impl

import kotlinx.coroutines.flow.Flow
import space.song.data.repository.AppPreferencesRepository
import space.song.datastore.AppPreferencesDatastore
import space.song.model.preferences.AppLaunchPreferences
import javax.inject.Inject

internal class AppPreferencesRepositoryImpl @Inject constructor(
    private val appPreferencesDatastore: AppPreferencesDatastore
) : AppPreferencesRepository {
    override fun appPreferences(): Flow<AppLaunchPreferences> = appPreferencesDatastore.appPreferencesFlow
}