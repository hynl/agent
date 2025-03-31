package space.song.network.model

sealed class ResponseResource<out T> {
    data class Success<out T>(val data: T) : ResponseResource<T>()
    data class Failure(val httpCode: Int, val errorCode: Int, val message: String? = null) : ResponseResource<Nothing>()
}
