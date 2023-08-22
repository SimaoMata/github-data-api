package mata.simao.control

import jakarta.enterprise.context.ApplicationScoped
import mata.simao.control.mapper.BranchMapper
import mata.simao.control.mapper.RepositoryMapper
import mata.simao.entity.Branch
import mata.simao.entity.Repository
import mata.simao.integration.GithubService
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.eclipse.microprofile.rest.client.inject.RestClient

@ApplicationScoped
class GithubControl(
    @ConfigProperty(name = "rest-client.repository-api.key")
    private val authorizationHeaderParam: String,
    @RestClient private val githubService: GithubService,
    private val repositoryMapper: RepositoryMapper,
    private val branchMapper: BranchMapper
) {
    fun getListOfRepositoriesByUsername(username: String): List<Repository> {
        return githubService
            .getUserRepositories(authorizationHeaderParam, username, "100", "1")
            .filter { it.fork == false || it.fork == null }
            .map { repositoryMapper.buildRepositoryResponse(it) }
            .map { it.also { repo -> repo.branches = getListOfBranchesByRepository(repo) } }
    }

    private fun getListOfBranchesByRepository(repo: Repository): List<Branch> =
        githubService
            .getRepositoryBranches(
                authorizationHeaderParam,
                repo.ownerLogin ?: "",
                repo.repositoryName ?: "",
                "100",
                "1"
            )
            .map { branchMapper.buildBranchResponse(it) }
}
