package mata.simao.control.mapper

import mata.simao.integration.entity.RepositoryDto
import mata.simao.integration.entity.RepositoryOwnerDto
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class RepositoryMapperTest {

    private val repositoryMapper = RepositoryMapper()

    @Test
    fun `should map all fields except branches`() {
        val repositoryOwnerDto = RepositoryOwnerDto(login = "testOwnerName")
        val repositoryDto = RepositoryDto(name = "testRepositoryName", owner = repositoryOwnerDto, fork = true)

        val repository = repositoryMapper.buildRepositoryResponse(repositoryDto)

        assertEquals("testRepositoryName", repository.repositoryName)
        assertEquals("testOwnerName", repository.ownerLogin)
        assert(repository.branches!!.isEmpty())
    }

    @Test
    fun `should not map fields`() {
        val repositoryDto = RepositoryDto()
        val repository = repositoryMapper.buildRepositoryResponse(repositoryDto)

        assertNull(repository.repositoryName)
        assertNull(repository.ownerLogin)
        assert(repository.branches!!.isEmpty())
    }

}