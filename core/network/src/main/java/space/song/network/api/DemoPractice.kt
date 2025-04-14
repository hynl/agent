package space.song.network.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import space.song.model.demo.DemoResponse

interface DemoApiService {
    @GET("posts/{id}")
    suspend fun getPost(@Path("id") id: Int): Response<DemoResponse>

    @GET("posts")
    suspend fun getAllPost(): Response<List<DemoResponse>> {
        return getAllPost()
    }
}
