package space.song.network.api

import retrofit2.http.GET
import retrofit2.http.Path
import space.song.network.model.ApiResponse

interface ApiService {
    @GET("posts/{id}")
    suspend fun getPost(@Path("id") id: Int): ApiResponse
}