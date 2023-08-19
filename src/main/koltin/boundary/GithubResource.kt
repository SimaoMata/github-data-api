package boundary

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path

@Path("/repository")
class GithubResource {

    @GET
    fun getHelloWorld() : String {
        return "Hello World"
    }

}