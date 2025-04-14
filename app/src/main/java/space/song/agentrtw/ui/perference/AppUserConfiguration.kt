package space.song.agentrtw.ui.perference

open class AppUserConfiguration(
    open val themeMode: ThemeMode = ThemeMode.SYSTEM,
    open val fontScale: Float = 1.0f,
    open val isFirstLaunch: Boolean = true
) {
    /**
     * 应用默认配置 - 系统预设的初始配置
     */
    class DefaultAppUserConfiguration : AppUserConfiguration(
        themeMode = ThemeMode.SYSTEM,
        fontScale = 1.0f,
        isFirstLaunch = true
    )

    /**
     * 用户偏好配置 - 基于用户明确设置的选项
     */
    class UserPreferenceConfiguration(
        override val themeMode: ThemeMode,
        override val fontScale: Float,
        val enableNotifications: Boolean = true,
        val dataSavingMode: Boolean = true
    ) : AppUserConfiguration(
        themeMode = themeMode,
        fontScale = fontScale,
        isFirstLaunch = false
    )

    /**
     * 智能配置 - AI根据用户习惯自动生成的配置
     */
    class AdaptiveConfiguration(
        override val themeMode: ThemeMode,
        override val fontScale: Float,
        val suggestedFeatures: List<String>,
        val usagePattern: UsagePattern,
        val preferenceLanguage: PreferenceLanguage
    ) : AppUserConfiguration(
        themeMode = themeMode,
        fontScale = fontScale,
        isFirstLaunch = false
    )
}

/**
 * 主题模式枚举
 */
enum class ThemeMode {
    LIGHT, DARK, SYSTEM, DYNAMIC,
}

/**
 * 使用模式枚举
 */
enum class UsagePattern {
    CASUAL, FREQUENT, POWER_USER, MINIMAL
}

/**
 * 语言枚举
 */
enum class PreferenceLanguage {
    ENGLISH, CHINESE, SPANISH, FRENCH, GERMAN, JAPANESE, KOREAN, RUSSIAN
}
