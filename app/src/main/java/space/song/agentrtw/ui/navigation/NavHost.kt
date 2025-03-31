package space.song.agentrtw.ui.navigation

@Composable
fun AgentNavHost() {
    // Navigation host implementation
    NavHost(
        navController = rememberNavController(),
        startDestination = "home"
    ) {
        composable("home") { HomeScreen() }
        composable("settings") { SettingsScreen() }
        // Add more destinations here
    }
}