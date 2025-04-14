package space.song.agentrtw.ui.perference

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

fun appPreferenceState(): MutableState<AppUserConfiguration> = mutableStateOf<AppUserConfiguration>(AppUserConfiguration.DefaultAppUserConfiguration())


