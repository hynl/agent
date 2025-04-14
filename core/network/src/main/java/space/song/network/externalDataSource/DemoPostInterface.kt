package space.song.network.externalDataSource

import space.song.model.demo.DemoResponse
import space.song.network.model.ResponseResource

interface DemoPostInterface {
    suspend fun getPost(id: Int): ResponseResource<DemoResponse>
    suspend fun getAllPosts(): ResponseResource<List<DemoResponse>>
}
