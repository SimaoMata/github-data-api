package mata.simao.control.mapper

import jakarta.enterprise.context.ApplicationScoped
import mata.simao.entity.Repository
import mata.simao.integration.entity.RepositoryDto

@ApplicationScoped
class RepositoryMapper {
    fun buildRepositoryResponse(repositoryDto: RepositoryDto) =
        Repository(
            repositoryName = repositoryDto.name,
            ownerLogin = repositoryDto.owner?.login
        )
}