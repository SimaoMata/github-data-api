package mata.simao.integration.entity
data class RepositoryDto(var name: String? = null, var owner: RepositoryOwnerDto? = null, var fork: Boolean? = null)
data class RepositoryOwnerDto(var login: String? = null)
