package space.song.agentrtw

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import space.song.domain.preference.AppPreferenceManager
import space.song.model.preferences.AppLaunchPreferences
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appPreferenceManager: AppPreferenceManager,
): ViewModel() {
    val appUiState: StateFlow<MainUiState> = appPreferenceManager.appPreference.map {
        MainUiState.Success(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = MainUiState.Loading,
        started = SharingStarted.WhileSubscribed(5_000),
    )
}

sealed interface MainUiState {
    data object Loading : MainUiState
    data class Success(val appPreferences: AppLaunchPreferences) : MainUiState
    data class Error(val error: Throwable) : MainUiState

    fun shouldKeepSplashScreen() = this is Loading
}