package mata.simao.control.mapper

import mata.simao.integration.entity.BranchCommitDto
import mata.simao.integration.entity.BranchDto
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class BranchMapperTest {

    private val branchMapper = BranchMapper()

    @Test
    fun `should map all fields`() {
        val branchCommitDto = BranchCommitDto(sha = "123")
        val branchDto = BranchDto(name = "testBranchName", branchCommitDto)

        val branch = branchMapper.buildBranchResponse(branchDto)

        assertEquals("testBranchName", branch.name)
        assertEquals("123", branch.lastCommitSha)
    }

    @Test
    fun `should not map fields`() {
        val branchDto = BranchDto()

        val branch = branchMapper.buildBranchResponse(branchDto)

        assertNull(branch.name)
        assertNull(branch.lastCommitSha)
    }

}