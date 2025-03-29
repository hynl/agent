package space.song.network.model

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure(val httpCode: Int, val errorCode: Int, val message: String? = null) : Resource<Nothing>()
}
