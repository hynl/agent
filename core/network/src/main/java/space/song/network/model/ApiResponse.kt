package space.song.network.model

data class ApiResponse(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)
