package mata.simao.integration.entity

data class RepositoriesResponseDto(var repositories: List<RepositoryDto>)
data class RepositoryDto(var name: String? = null, var owner: RepositoryOwnerDto? = null, var fork: Boolean? = null)
data class RepositoryOwnerDto(var login: String? = null)
