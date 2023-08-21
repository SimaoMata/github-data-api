package mata.simao.entity

data class UserRepository(
    var repositoryName: String? = null,
    var ownerLogin: String? = null,
    var branches: List<RepositoryBranch>? = emptyList()
)