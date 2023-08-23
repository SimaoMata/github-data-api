package mata.simao.boundary

import jakarta.ws.rs.core.Response
import mata.simao.control.GithubControl
import mata.simao.entity.Repository
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import kotlin.test.assertEquals


internal class GithubResourceTest {

    private val mockedGithubControl = Mockito.mock(GithubControl::class.java)
    private val githubResource = GithubResource(mockedGithubControl)

    @Test
    fun `getUserRepositories should return a response`() {
        val repository = listOf(
            Repository(
                repositoryName = "testRepositoryName",
                ownerLogin = "testOwnerLogin"
            )
        )

        Mockito
            .`when`(mockedGithubControl.getListOfRepositoriesByUsername(anyString()))
            .thenReturn(repository)

        val response = githubResource.getUserRepositories("testUsername")
        assertEquals(Response.Status.OK.statusCode, response.status)
        assertEquals(repository, response.entity)
    }

    @Test
    fun `getUserRepositories should return a response with an empty list`() {
        Mockito
            .`when`(mockedGithubControl.getListOfRepositoriesByUsername(anyString()))
            .thenReturn(emptyList())

        val response = githubResource.getUserRepositories("testUsername")
        assertEquals(Response.Status.OK.statusCode, response.status)
        assertEquals(emptyList<Repository>(), response.entity)
    }

}