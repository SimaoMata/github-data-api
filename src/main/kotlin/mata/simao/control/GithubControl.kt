package mata.simao.control

import jakarta.enterprise.context.ApplicationScoped
import mata.simao.control.mapper.BranchMapper
import mata.simao.control.mapper.RepositoryMapper
import mata.simao.entity.Branch
import mata.simao.entity.Repository
import mata.simao.integration.GithubService
import mata.simao.integration.entity.RepositoryDto
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
    companion object {
        const val PER_PAGE_VALUE = 1
    }

    fun getListOfRepositoriesByUsername(username: String): List<Repository> {
        return getAllUserRepositories(username)
            .filter { it.fork == false || it.fork == null }
            .map { repositoryMapper.buildRepositoryResponse(it) }
            .map { it.also { repo -> repo.branches = getAllRepositoryBranches(repo) } }
    }

    private fun getAllUserRepositories(username: String): List<RepositoryDto> =
        generateSequence(1) { it + 1 }
            .map { pageNr ->
                githubService.getUserRepositories(authorizationHeaderParam, username, PER_PAGE_VALUE, pageNr)
            }
            .takeWhile { it.isNotEmpty() }
            .flatten()
            .toList()

    private fun getAllRepositoryBranches(repo: Repository): List<Branch> =
        generateSequence(1) { it + 1 }
            .map { pageNr ->
                githubService.getRepositoryBranches(
                    authorizationHeaderParam,
                    repo.ownerLogin,
                    repo.repositoryName,
                    PER_PAGE_VALUE,
                    pageNr
                )
            }
            .takeWhile { it.isNotEmpty() }
            .flatten()
            .map { branchMapper.buildBranchResponse(it) }
            .toList()
}
