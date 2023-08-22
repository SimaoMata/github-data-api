package mata.simao.boundary

import jakarta.enterprise.context.ApplicationScoped
import jakarta.json.Json
import jakarta.json.JsonObject
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
        if (acceptHeader == "application/xml") {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(
                Json.createArrayBuilder()
            ).build()
        }
        return githubControl.getListOfRepositoriesByUsername(username)
            .let { Response.ok().entity(it).build() }
            ?: Response.status(Response.Status.NOT_FOUND).build()
    }
}

