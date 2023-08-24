package mata.simao.boundary

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured
import io.restassured.http.ContentType
import io.restassured.http.Header
import io.restassured.path.json.JsonPath
import jakarta.ws.rs.core.Response.Status
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


@QuarkusTest
internal class GithubResourceIT {

    private val wiremockServer = WireMockServer(7777)

    companion object {
        private const val VALID_USERNAME = "validUsername"
        private const val INVALID_USERNAME = "invalidUsername"
        private const val VALID_REPO = "Hello-World"
        private const val PER_PAGE = 100

        private const val GET_REPOSITORIES_BY_USER_V3 = "/users/%s/repos?per_page=%s&page=%s"
        private const val GET_BRANCHES_BY_REPOSITORY_V3 = "/repos/%s/%s/branches?per_page=%s&page=%s"

        private const val GET_USER_REPOSITORIES_URL = "/api/repository?user=%s"

        private const val ACCEPT_HEADER = "Accept"
        private val JSON_ACCEPT_HEADER = Header(ACCEPT_HEADER, "application/json")
        private val XML_ACCEPT_HEADER = Header(ACCEPT_HEADER, "application/xml")
    }

    private fun getFileContentAsString(resourcePath: String): String? =
        javaClass.classLoader.getResource(resourcePath)?.readText(Charsets.UTF_8)

    @BeforeEach
    fun setup() {
        val successfulRepositoryResponse =
            JsonPath(getFileContentAsString("wiremock/data/repositories.json"))
        wiremockServer.stubFor(
            WireMock.get(GET_REPOSITORIES_BY_USER_V3.format(VALID_USERNAME, PER_PAGE, 1))
                .willReturn(WireMock.okJson(successfulRepositoryResponse.prettify()))
        )
        wiremockServer.stubFor(
            WireMock.get(GET_REPOSITORIES_BY_USER_V3.format(VALID_USERNAME, PER_PAGE, 2))
                .willReturn(
                    WireMock.okJson("[]")
                )
        )
        val repositoryNotFoundResponse =
            JsonPath(getFileContentAsString("wiremock/data/repositoriesNotFound.json"))
        wiremockServer.stubFor(
            WireMock.get(GET_REPOSITORIES_BY_USER_V3.format(INVALID_USERNAME, PER_PAGE, 1))
                .willReturn(
                    aResponse().withStatus(404).withBody(repositoryNotFoundResponse.prettify())
                )
        )

        val successfulBranchResponse = JsonPath(getFileContentAsString("wiremock/data/branches.json"))
        wiremockServer.stubFor(
            WireMock.get(GET_BRANCHES_BY_REPOSITORY_V3.format(VALID_USERNAME, VALID_REPO, PER_PAGE, 1))
                .willReturn(WireMock.okJson(successfulBranchResponse.prettify()))
        )
        wiremockServer.stubFor(
            WireMock.get(GET_BRANCHES_BY_REPOSITORY_V3.format(VALID_USERNAME, VALID_REPO, PER_PAGE, 2))
                .willReturn(
                    WireMock.okJson("[]")
                )
        )
    }

    @AfterEach
    fun tearDown() {
        wiremockServer.resetAll()
    }

    @Test
    fun `getUserRepositories should return 200 with a list of repositories`() {
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(JSON_ACCEPT_HEADER)
            .`when`().get(GET_USER_REPOSITORIES_URL.format(VALID_USERNAME))
            .then().statusCode(Status.OK.statusCode)
    }

    @Test
    fun `getUserRepositories should return 404`() {
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(JSON_ACCEPT_HEADER)
            .`when`().get(GET_USER_REPOSITORIES_URL.format(INVALID_USERNAME))
            .then().statusCode(Status.NOT_FOUND.statusCode)
    }

    @Test
    fun `getUserRepositories should return 406`() {
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header(XML_ACCEPT_HEADER)
            .`when`().get(GET_USER_REPOSITORIES_URL.format(VALID_USERNAME))
            .then().statusCode(Status.NOT_ACCEPTABLE.statusCode)
    }
}