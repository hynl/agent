package space.song.domain.preference

import kotlinx.coroutines.flow.Flow
import space.song.model.preferences.AppLaunchPreferences

interface AppPreferenceManager {
    val appPreference: Flow<AppLaunchPreferences>
}
