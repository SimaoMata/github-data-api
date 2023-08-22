package mata.simao.entity

data class Repository(
    var repositoryName: String? = null,
    var ownerLogin: String? = null,
    var branches: List<Branch>? = emptyList()
)