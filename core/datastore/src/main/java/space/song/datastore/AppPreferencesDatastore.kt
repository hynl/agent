package space.song.datastore

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.map
import space.song.datastore.proto.AppPreferences
import space.song.model.preferences.AppLaunchPreferences
import javax.inject.Inject

class AppPreferencesDatastore @Inject constructor(
    private val appPreferences: DataStore<AppPreferences>
) {
    val appPreferencesFlow = appPreferences.data
        .map { preferences ->
            AppLaunchPreferences(
                isFirstLaunch = preferences.isFirstLaunch,
                autoFlags = preferences.autoFlags,
                themeFlags = preferences.themeFlags,
                loginFlags = preferences.loginFlags,
                appModeFlags = preferences.appModeFlags
            )
        }

    suspend fun setFirstLaunch(isFirstLaunch: Boolean) {
        appPreferences.updateData { preferences ->
            preferences.toBuilder()
                .setIsFirstLaunch(isFirstLaunch)
                .build()
        }
    }

    suspend fun setAutoFlags(flags: Int) {
        appPreferences.updateData { preferences ->
            preferences.toBuilder()
                .setAutoFlags(flags)
                .build()
        }
    }

    suspend fun setThemeFlags(flags: Int) {
        appPreferences.updateData { preferences ->
            preferences.toBuilder()
                .setThemeFlags(flags)
                .build()
        }
    }

    suspend fun setLoginFlags(flags: Int) {
        appPreferences.updateData { preferences ->
            preferences.toBuilder()
                .setLoginFlags(flags)
                .build()
        }
    }

    suspend fun setAppModeFlags(flags: Int) {
        appPreferences.updateData { preferences ->
            preferences.toBuilder()
                .setAppModeFlags(flags)
                .build()
        }
    }
}
