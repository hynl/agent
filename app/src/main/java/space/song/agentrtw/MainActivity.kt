package space.song.agentrtw

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import space.song.agentrtw.ui.perference.AppUserConfiguration
import space.song.agentrtw.ui.perference.ThemeMode
import space.song.agentrtw.ui.perference.appPreferenceState
import space.song.agentrtw.ui.rememberRtwAppState
import space.song.component.ex.isSystemInDarkTheme
import space.song.component.ex.observeWhenStarted
import space.song.component.theme.AgentRTWTheme
import space.song.data.util.NetworkMonitor
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        var currentPreferenceState by appPreferenceState()

        observeWhenStarted {
            combine(
                isSystemInDarkTheme(),
                mainViewModel.appUiState
            ) { isDarkTheme, appUiState ->
                AppUserConfiguration.UserPreferenceConfiguration(
                    themeMode = if (isDarkTheme) {
                        ThemeMode.DARK
                    } else {
                        ThemeMode.LIGHT
                    },
                    fontScale = 1f
                )
            }
                .onEach { newPreferenceState ->
                    currentPreferenceState = newPreferenceState
                }
                .map { it.themeMode }
                .distinctUntilChanged()
                .collect { themeMode ->

                    // Turn off the decor fitting system windows, which allows us to handle insets,
                    // including IME animations, and go edge-to-edge.
                    // This is the same parameters as the default enableEdgeToEdge call, but we manually
                    // resolve whether or not to show dark theme using uiState, since it can be different
                    // than the configuration's dark theme value based on the user preference.
                    enableEdgeToEdge(
                        statusBarStyle = SystemBarStyle.auto(
                            lightScrim = android.graphics.Color.TRANSPARENT,
                            darkScrim = android.graphics.Color.TRANSPARENT,
                        ) { themeMode == ThemeMode.DARK },
                        navigationBarStyle = SystemBarStyle.auto(
                            lightScrim = lightScrim,
                            darkScrim = darkScrim,
                        ) { themeMode == ThemeMode.DARK },
                    )
                }
        }

        splashScreen.setKeepOnScreenCondition { mainViewModel.appUiState.value.shouldKeepSplashScreen() }

        setContent {

            val appState = rememberRtwAppState(
                networkMonitor = networkMonitor
            )
            AgentRTWTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                    RtwApp()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name! ？？？",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AgentRTWTheme {
        Greeting("Android")
    }
}

/**
 * The default light scrim, as defined by androidx and the platform:
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=35-38;drc=27e7d52e8604a080133e8b842db10c89b4482598
 */
private val lightScrim = android.graphics.Color.argb(0xe6, 0xFF, 0xFF, 0xFF)

/**
 * The default dark scrim, as defined by androidx and the platform:
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=40-44;drc=27e7d52e8604a080133e8b842db10c89b4482598
 */
private val darkScrim = android.graphics.Color.argb(0x80, 0x1b, 0x1b, 0x1b)