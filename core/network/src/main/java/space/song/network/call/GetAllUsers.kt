package space.song.network.call
//
//获取所有帖子：https://jsonplaceholder.typicode.com/posts
//获取单个帖子：https://jsonplaceholder.typicode.com/posts/{id}
//获取所有用户：https://jsonplaceholder.typicode.com/users
//获取单个用户：https://jsonplaceholder.typicode.com/users/{id}

import space.song.network.api.ApiService
import space.song.network.model.Resource
import space.song.network.safeApiCall

class Repository(private val apiService: ApiService) {

    suspend fun getUser(id: Int): Resource<ApiSuccessResponse> {
        return safeApiCall { apiService.getUser(id) }
    }
}