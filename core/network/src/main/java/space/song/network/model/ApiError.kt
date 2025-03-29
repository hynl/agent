package space.song.network.model

data class ApiError(
    val httpcode: Int,
    val code: Int,
    val module: String?,
)