package mata.simao.boundary

import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.QueryParam
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import mata.simao.control.GithubControl

@ApplicationScoped
@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
class GithubResource(
    private val githubControl: GithubControl
) {

    @GET
    @Path("/repositories")
    fun getUserRepositories(
        @QueryParam("user") username: String
    ): Response = githubControl
        .getListOfRepositoriesByUsername(username)
        .let { Response.ok(it).build() }
}

