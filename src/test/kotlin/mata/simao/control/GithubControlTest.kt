package mata.simao.control

import koltin.integration.RepositoriesNotFoundException
import mata.simao.control.mapper.BranchMapper
import mata.simao.control.mapper.RepositoryMapper
import mata.simao.integration.GithubService
import mata.simao.integration.entity.RepositoryDto
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import java.util.stream.Stream
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

internal class GithubControlTest {

    private val repositoryMapper = RepositoryMapper()
    private val branchMapper = BranchMapper()
    private val mockedGithubService = Mockito.mock(GithubService::class.java)
    private val githubControl = GithubControl("test", mockedGithubService, repositoryMapper, branchMapper)

    companion object {
        @JvmStatic
        private fun emptyListArgumentsProvider(): Stream<Arguments> = Stream.of(
            Arguments.of(emptyList<RepositoryDto>()),
            Arguments.of(
                listOf(RepositoryDto("test", null, true))
            )
        )
    }

    @ParameterizedTest
    @MethodSource("emptyListArgumentsProvider")
    fun `getListOfRepositoriesByUsername should return an empty list`(repositoryDtoList: List<RepositoryDto>) {
        Mockito.`when`(
            mockedGithubService.getUserRepositories(
                "test", "user", 100, 1
            )
        ).thenReturn(repositoryDtoList)

        val response = githubControl.getListOfRepositoriesByUsername("user")

        assert(response.isEmpty())
    }

    @Test
    fun `getListOfRepositoriesByUsername should return a list of repositories that are not forks`() {
        Mockito.`when`(
            mockedGithubService.getUserRepositories(
                "test", "user", 100, 1
            )
        ).thenReturn(
            listOf(RepositoryDto(fork = false), RepositoryDto(fork = false), RepositoryDto(fork = true))
        )

        Mockito.`when`(
            mockedGithubService.getUserRepositories(
                "test", "user", 100, 2
            )
        ).thenReturn(
            listOf(RepositoryDto(fork = false), RepositoryDto(fork = true), RepositoryDto(fork = true))
        )

        val response = githubControl.getListOfRepositoriesByUsername("user")

        assertEquals(3, response.size)
    }

    @Test
    fun `getListOfRepositoriesByUsername should throw RepositoriesNotFoundException`() {
        Mockito.`when`(
            mockedGithubService.getUserRepositories(
                anyString(), anyString(), anyInt(), anyInt()
            )
        ).thenThrow(RepositoriesNotFoundException)

        assertFailsWith<RepositoriesNotFoundException> {
            githubControl.getListOfRepositoriesByUsername("testName")
        }
    }


}