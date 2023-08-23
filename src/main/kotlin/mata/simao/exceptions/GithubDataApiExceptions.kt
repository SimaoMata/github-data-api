package mata.simao.exceptions

import jakarta.ws.rs.core.Response
import java.lang.RuntimeException

abstract class GithubDataApiException(val statusCode: Int, message: String) : RuntimeException(message)

object RepositoryNotFoundException : GithubDataApiException(
    statusCode = Response.Status.NOT_FOUND.statusCode,
    message = "The requested resource could not be found." +
            "There is no value available for a repository with the \"user\" query parameter."
)

object NotAcceptableException : GithubDataApiException(
    statusCode = Response.Status.NOT_FOUND.statusCode,
    message = "The server cannot provide the response in the requested format." +
            "The 'Accept' header in your request does not allow 'application/xml'." +
            "Please modify the 'Accept' header to include compatible formats, such as 'application/json'."
)