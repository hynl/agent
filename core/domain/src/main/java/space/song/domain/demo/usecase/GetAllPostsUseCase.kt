package space.song.domain.demo.usecase

import kotlinx.coroutines.flow.Flow
import space.song.data.repository.DemoNewsRepository
import space.song.model.demo.DemoModel
import javax.inject.Inject

class GetAllPostsUseCase @Inject constructor(
    private val demoRepository: DemoNewsRepository
) {
    fun execute(): Flow<List<DemoModel>> {
        return demoRepository.getAllNews()
    }
}
