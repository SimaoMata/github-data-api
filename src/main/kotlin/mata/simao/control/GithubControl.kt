package mata.simao.control

import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.context.Dependent
import jakarta.enterprise.inject.Stereotype
import mata.simao.control.mapper.RepositoryBranchMapper
import mata.simao.control.mapper.UserRepositoryMapper
import mata.simao.entity.RepositoryBranch
import mata.simao.entity.UserRepository
import mata.simao.integration.GithubService
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.eclipse.microprofile.rest.client.inject.RestClient

@ApplicationScoped
class GithubControl(
    @ConfigProperty(name = "github.data.api.key")
    private val authorizationHeaderParam: String,
    @RestClient private val githubService: GithubService,
    private val userRepositoryMapper: UserRepositoryMapper,
    private val repositoryBranchMapper: RepositoryBranchMapper
) {
    fun getListOfRepositoriesByUsername(username: String): List<UserRepository> {
        println("user inside control : $username")
        return githubService
            .getUserRepositories(authorizationHeaderParam, username, "100", "1")
            .filter { it.fork == false || it.fork == null }
            .map { userRepositoryMapper.buildRepositoryResponse(it) }
            .map { it.also { repo -> repo.branches = getListOfBranchesByRepository(repo) } }
    }

    private fun getListOfBranchesByRepository(repo: UserRepository): List<RepositoryBranch> =
        githubService
            .getRepositoryBranches(
                authorizationHeaderParam,
                repo.ownerLogin ?: "",
                repo.repositoryName ?: "",
                "100",
                "1"
            )
            .map { repositoryBranchMapper.buildBranchResponse(it) }

    fun abc(): String {
        return githubService.abc()
    }
}
