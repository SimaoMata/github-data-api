package koltin.integration

import jakarta.ws.rs.InternalServerErrorException
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper
import java.lang.RuntimeException

class RestClientExceptionMapper : ResponseExceptionMapper<RuntimeException> {
    override fun toThrowable(response: Response): RuntimeException {
        return when (response.status) {
            Response.Status.NOT_FOUND.statusCode -> RepositoriesNotFoundException
            else -> {
                InternalServerErrorException("AAAAAA")
            }
        }
    }
}

object RepositoriesNotFoundException : RuntimeException("repository not found ") {
    private fun readResolve(): Any = RepositoriesNotFoundException
}