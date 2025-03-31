package space.song.network.externalDataSource
//
//获取所有帖子：https://jsonplaceholder.typicode.com/posts
//获取单个帖子：https://jsonplaceholder.typicode.com/posts/{id}
//获取所有用户：https://jsonplaceholder.typicode.com/users
//获取单个用户：https://jsonplaceholder.typicode.com/users/{id}

import space.song.model.demo.DemoResponse
import space.song.network.api.DemoApiService
import space.song.network.model.ResponseResource
import space.song.network.safeApiCall

class DemoPostImpl(private val apiService: DemoApiService) : DemoPostInterface {

    override suspend fun getPost(id: Int): ResponseResource<DemoResponse> {
        return safeApiCall(
            call = { apiService.getPost(id) }
        )
    }
}
