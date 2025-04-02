package space.song.agentrtw.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun AgentNavHost(
    //appState: AgentAppState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
) {
    // Navigation host implementation
//    NavHost(
//        navController = rememberNavController(),
//        startDestination = "home"
//    ) {
//
//        // Add more destinations here
//    }
}