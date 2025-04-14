package space.song.model.preferences

// 自动功能标志
object AutoFlags {
    const val RECOVER = 0x00000001
    const val HELLO = 0x00000002
    const val UPDATE = 0x00000004
    const val SYNC = 0x00000008
    // 可以继续添加更多自动功能标志
}

// 主题相关标志
object ThemeFlags {
    const val DARK_MODE = 0x00000001
    const val LIGHT_MODE = 0x00000002
    const val SYSTEM_MODE = 0x00000004
    const val HIGH_CONTRAST = 0x00000008
    // 可以继续添加更多主题相关标志
}

// 登录状态标志
object LoginFlags {
    const val LOGIN_MODE = 0x00000001
    const val GUEST_MODE = 0x00000002
    const val LOGOUT_MODE = 0x00000004
    const val ANONYMOUS_MODE = 0x00000008
    // 可以继续添加更多登录状态标志
}

// 应用模式标志
object AppModeFlags {
    const val GENERIC_MODE = 0x00000001
    const val AGENT_MODE = 0x00000002
    const val PRO_MODE = 0x00000004
    const val DEBUG_MODE = 0x00000008
    // 可以继续添加更多应用模式标志
}

data class AppLaunchPreferences(
    val isFirstLaunch: Boolean = true,
    var autoFlags: Int = 0,
    var themeFlags: Int = ThemeFlags.SYSTEM_MODE,
    var loginFlags: Int = LoginFlags.LOGOUT_MODE,
    var appModeFlags: Int = AppModeFlags.GENERIC_MODE
) {
    // 自动功能标志方法
    fun hasAutoFlag(flag: Int): Boolean = (autoFlags and flag) != 0
    fun setAutoFlag(flag: Int, enabled: Boolean) {
        autoFlags = if (enabled) autoFlags or flag else autoFlags and flag.inv()
    }
    // 主题标志方法
    fun setThemeMode(mode: Int) {
        // 清除现有主题模式标志
        themeFlags = 0
        // 设置新主题模式
        themeFlags = themeFlags or mode
    }
    fun isDarkMode() = (themeFlags and ThemeFlags.DARK_MODE) != 0
    fun isLightMode() = (themeFlags and ThemeFlags.LIGHT_MODE) != 0
    fun isSystemMode() = (themeFlags and ThemeFlags.SYSTEM_MODE) != 0


    // 登录状态方法
    fun setLoginMode(mode: Int) {
        // 清除现有登录模式标志
        loginFlags = 0
        // 设置新登录模式
        loginFlags = loginFlags or mode
    }
    fun isLoggedIn() = (loginFlags and LoginFlags.LOGIN_MODE) != 0
    fun isGuest() = (loginFlags and LoginFlags.GUEST_MODE) != 0
    fun isLoggedOut() = (loginFlags and LoginFlags.LOGOUT_MODE) != 0


    // 应用模式方法
    fun setAppMode(mode: Int) {
        // 清除现有应用模式标志
        appModeFlags = 0
        // 设置新应用模式
        appModeFlags = appModeFlags or mode
    }
    fun isGenericMode() = (appModeFlags and AppModeFlags.GENERIC_MODE) != 0
    fun isAgentMode() = (appModeFlags and AppModeFlags.AGENT_MODE) != 0

    companion object {
        // 创建默认配置
        fun default() = AppLaunchPreferences(
            isFirstLaunch = true,
            themeFlags = ThemeFlags.SYSTEM_MODE,
            loginFlags = LoginFlags.LOGOUT_MODE,
            appModeFlags = AppModeFlags.GENERIC_MODE
        )

        // 创建用户偏好配置
        fun userPreference(themeMode: Int = ThemeFlags.LIGHT_MODE, loginMode: Int = LoginFlags.LOGIN_MODE) =
            AppLaunchPreferences(
                isFirstLaunch = false,
                themeFlags = themeMode,
                loginFlags = loginMode,
                appModeFlags = AppModeFlags.GENERIC_MODE
            )

        // ��建AI适应性配置
        fun adaptiveConfig(autoFeatures: Int) = AppLaunchPreferences(
            isFirstLaunch = false,
            autoFlags = autoFeatures,
            themeFlags = ThemeFlags.SYSTEM_MODE,
            loginFlags = LoginFlags.LOGIN_MODE,
            appModeFlags = AppModeFlags.AGENT_MODE
        )
    }
}
