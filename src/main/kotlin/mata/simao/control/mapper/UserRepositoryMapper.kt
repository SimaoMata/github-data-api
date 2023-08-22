package mata.simao.control.mapper

import jakarta.enterprise.context.ApplicationScoped
import mata.simao.entity.UserRepository
import mata.simao.integration.entity.RepositoryDto

@ApplicationScoped
class UserRepositoryMapper {
    fun buildRepositoryResponse(repositoryDto: RepositoryDto) =
        UserRepository(
            repositoryName = repositoryDto.name,
            ownerLogin = repositoryDto.owner?.login
        )
}