package mata.simao.exceptions

import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
class GithubDataApiExceptionMapper : ExceptionMapper<GithubDataApiException> {
    override fun toResponse(exception: GithubDataApiException): Response {
        return Response.status(exception.statusCode, exception.message).build()
    }

}

@Provider
class FallbackExceptionMapper : ExceptionMapper<Throwable> {
    override fun toResponse(exception: Throwable): Response {
        return Response.serverError().build()
    }

}