package space.song.data.repository

import kotlinx.coroutines.flow.Flow
import space.song.model.preferences.AppLaunchPreferences

interface AppPreferencesRepository {
    fun appPreferences(): Flow<AppLaunchPreferences>
}