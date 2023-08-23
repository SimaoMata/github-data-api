package mata.simao.boundary

import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.*
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
    @Path("/repository")
    fun getUserRepositories(
        @HeaderParam("Accept") acceptHeader: String,
        @QueryParam("user") username: String
    ): Response {
        return githubControl.getListOfRepositoriesByUsername(username)
            .let { Response.ok(it).build() }
    }
}

