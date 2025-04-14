package space.song.domain.demo

import space.song.domain.demo.usecase.GetAllPostsUseCase
import javax.inject.Inject

internal class DemoDomainManagerImpl @Inject constructor(
    private val getAllPostsUseCase: GetAllPostsUseCase,
) : DemoDomainManager {
    override fun getAllPosts() = getAllPostsUseCase.execute()
}
