package mata.simao.integration

import mata.simao.integration.entity.BranchesResponseDto
import mata.simao.integration.entity.RepositoriesResponseDto
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import mata.simao.integration.entity.BranchDto
import mata.simao.integration.entity.RepositoryDto
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient

@Path("/")
@RegisterRestClient(configKey = "repository-api")
@RegisterClientHeaders
//@RegisterProvider(RestClientExceptionMapper::class)
interface GithubService {
    @GET
    @Path("/users/{username}/repos")
    fun getUserRepositories(
        @HeaderParam("Authorization") authorization: String,
        @PathParam("username") username: String,
        @QueryParam("per_page") perPage: String,
        @QueryParam("page") page: String
    ): List<RepositoryDto>

    @GET
    @Path("/repos/{username}/{repo}/branches")
    fun getRepositoryBranches(
        @HeaderParam("Authorization") authorization: String,
        @PathParam("username") username: String,
        @PathParam("repo") repo: String,
        @QueryParam("per_page") perPage: String,
        @QueryParam("page") page: String
    ): List<BranchDto>

    @GET
    @Path("/octocat")
    @Produces(MediaType.APPLICATION_JSON)
    fun abc(): String

}