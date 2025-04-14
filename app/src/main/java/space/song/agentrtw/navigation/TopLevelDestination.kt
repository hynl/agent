package space.song.agentrtw.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.ui.graphics.vector.ImageVector
import space.song.agentrtw.R
import kotlin.reflect.KClass

enum class TopLevelDestination (
    val selectIcon: ImageVector,
    val unSelectIcon: ImageVector,
    @StringRes val iconTextId: Int,
    @StringRes val titleTextId: Int,
    val route: KClass<*>,
    val baseRoute: KClass<*> = route,
) {
    FOCUS_PAD(
        selectIcon = Icons.Rounded.Info,
        unSelectIcon = Icons.Rounded.Info,
        iconTextId = R.string.base_focus_pad,
        titleTextId = R.string.base_focus_pad,
        route = FocusPadRoute::class,
        baseRoute = BaseFocusPadRoute::class,

    ),

    LIFE_COMPASS(
        selectIcon = Icons.Rounded.Info,
        unSelectIcon = Icons.Rounded.Info,
        iconTextId = R.string.base_life_compass,
        titleTextId = R.string.base_life_compass,
        route = LifeCompassRoute::class,
        baseRoute = BaseLifeCompassRoute::class,
    ),

    MY_ZONE(
        selectIcon = Icons.Rounded.Info,
        unSelectIcon = Icons.Rounded.Info,
        iconTextId = R.string.base_my_zone,
        titleTextId = R.string.base_my_zone,
        route = MyZoneRoute::class,
        baseRoute = BaseMyZoneRoute::class,
    )
}
