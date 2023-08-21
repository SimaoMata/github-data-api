package mata.simao.control.mapper

import mata.simao.entity.RepositoryBranch
import mata.simao.integration.entity.BranchDto
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class RepositoryBranchMapper {
    fun buildBranchResponse(branchDto: BranchDto) =
        RepositoryBranch(
            name = branchDto.name,
            lastCommitSha = branchDto.commit?.sha
        )
}