package mata.simao.control.mapper

import mata.simao.entity.UserRepository
import mata.simao.integration.entity.RepositoryDto
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserRepositoryMapper {
    fun buildRepositoryResponse(repositoryDto: RepositoryDto) =
        UserRepository(
            repositoryName = repositoryDto.name,
            ownerLogin = repositoryDto.owner?.login
        )
}