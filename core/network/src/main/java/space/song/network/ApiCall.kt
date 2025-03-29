package space.song.network

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import space.song.network.model.ApiError
import space.song.network.model.Resource
import java.io.IOException

suspend fun <T> safeApiCall(
    call: suspend () -> Response<T>,
    errorParser: (ResponseBody?) -> ApiError? = { errorBody ->
        try {
            errorBody?.string()?.let { Gson().fromJson(it, ApiError::class.java) }
        } catch (e: Exception) {
            null
        }
    }
): Resource<T> {
    return withContext(Dispatchers.IO) {
        try {
            val response = call()
            if (response.isSuccessful) {
                response.body()?.let { Resource.Success(it) }
                    ?: Resource.Failure(response.code(), -1, "Empty response body")
            } else {
                val apiError = errorParser(response.errorBody())
                Resource.Failure(response.code(), apiError?.code ?: -1, "Error occurred")
            }
        } catch (e: HttpException) {
            Resource.Failure(e.code(), -1, e.message())
        } catch (e: IOException) {
            Resource.Failure(-1, -1, "Network Error: ${e.localizedMessage}")
        }
    }
}