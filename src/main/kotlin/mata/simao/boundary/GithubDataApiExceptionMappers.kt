package mata.simao.boundary

import jakarta.json.Json
import jakarta.ws.rs.NotAcceptableException
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider
import koltin.integration.RepositoriesNotFoundException

@Provider
class AcceptHeaderParamNotAcceptableExceptionMapper : ExceptionMapper<NotAcceptableException> {
    override fun toResponse(exception: NotAcceptableException): Response =
        Response
            .status(Response.Status.NOT_ACCEPTABLE)
            .type(MediaType.APPLICATION_JSON_TYPE)
            .entity(
                Json.createObjectBuilder()
                    .add("status", Response.Status.NOT_ACCEPTABLE.statusCode)
                    .add(
                        "Message", "The server cannot provide the response in the requested format. " +
                                "Please modify the 'Accept' header to include compatible formats, such as 'application/json'."

                    )
                    .build()
            ).build()
}

@Provider
class RespositoryNotFoundExceptionMapper : ExceptionMapper<RepositoriesNotFoundException> {
    override fun toResponse(exception: RepositoriesNotFoundException): Response =
        Response
            .status(Response.Status.NOT_FOUND)
            .entity(
                Json.createObjectBuilder()
                    .add("status", Response.Status.NOT_FOUND.statusCode)
                    .add(
                        "Message",
                        "The requested resource could not be found. There is no repository with that given username."
                    )
                    .build()
            )
            .build()
}

@Provider
class FallbackExceptionMapper : ExceptionMapper<Throwable> {
    override fun toResponse(throwable: Throwable): Response {
        return Response.serverError().build()
    }

}
