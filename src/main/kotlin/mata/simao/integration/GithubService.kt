package mata.simao.integration

import jakarta.ws.rs.*
import koltin.integration.RestClientExceptionMapper
import mata.simao.integration.entity.BranchDto
import mata.simao.integration.entity.RepositoryDto
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient

@Path("/")
@RegisterRestClient(configKey = "repository-api")
@RegisterClientHeaders
@RegisterProvider(RestClientExceptionMapper::class)
interface GithubService {
    @GET
    @Path("/users/{username}/repos")
    fun getUserRepositories(
        @HeaderParam("Authorization") authorization: String?,
        @PathParam("username") username: String?,
        @QueryParam("per_page") perPage: Int?,
        @QueryParam("page") page: Int?
    ): List<RepositoryDto>

    @GET
    @Path("/repos/{username}/{repo}/branches")
    fun getRepositoryBranches(
        @HeaderParam("Authorization") authorization: String,
        @PathParam("username") username: String?,
        @PathParam("repo") repo: String?,
        @QueryParam("per_page") perPage: Int?,
        @QueryParam("page") page: Int?
    ): List<BranchDto>
}