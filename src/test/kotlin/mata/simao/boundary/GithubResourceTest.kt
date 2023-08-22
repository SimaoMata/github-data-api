package mata.simao.boundary

import mata.simao.control.GithubControl
import org.mockito.Mockito


internal class GithubResourceTest {

    private val mockedGithubControl = Mockito.mock(GithubControl::class.java)
    private val githubResource = GithubResource(mockedGithubControl)

    fun `getUserRepositories should return a response`() {}
    fun `getUserRepositories should return a response with an empty list`() {}

}