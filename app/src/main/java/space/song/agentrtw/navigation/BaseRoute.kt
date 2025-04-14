package space.song.agentrtw.navigation

import kotlinx.serialization.Serializable

@Serializable
data object BaseFocusPadRoute //焦点分离仓
//：舱内资讯 (Pod News)
//：回响捕手 (Echo Catcher)

@Serializable
data object BaseLifeCompassRoute // 生活指南针
//：寻宝针 (Deal Finder / Treasure Needle)
//：出行舵手 (Journey Helm)

@Serializable
data object BaseMyZoneRoute // 我的地带


