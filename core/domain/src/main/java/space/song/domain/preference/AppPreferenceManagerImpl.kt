package space.song.domain.preference

import kotlinx.coroutines.flow.Flow
import space.song.data.repository.AppPreferencesRepository
import space.song.model.preferences.AppLaunchPreferences
import javax.inject.Inject

internal class AppPreferenceManagerImpl @Inject constructor(
    private val appPreferencesRepository: AppPreferencesRepository
) : AppPreferenceManager {
    override val appPreference: Flow<AppLaunchPreferences>
        get() = appPreferencesRepository.appPreferences()
}
