package space.song.domain.demo

data class DemoResponse(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)

fun DemoResponse.toDomain(): DemoModel {
    return DemoModel(
        title = title,
        body = body
    )
}

