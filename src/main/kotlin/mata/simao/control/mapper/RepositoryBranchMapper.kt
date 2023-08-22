package mata.simao.control.mapper

import jakarta.enterprise.context.ApplicationScoped
import mata.simao.entity.RepositoryBranch
import mata.simao.integration.entity.BranchDto

@ApplicationScoped
class RepositoryBranchMapper {
    fun buildBranchResponse(branchDto: BranchDto) =
        RepositoryBranch(
            name = branchDto.name,
            lastCommitSha = branchDto.commit?.sha
        )
}