package mata.simao.control.mapper

import jakarta.enterprise.context.ApplicationScoped
import mata.simao.entity.Branch
import mata.simao.integration.entity.BranchDto

@ApplicationScoped
class BranchMapper {
    fun buildBranchResponse(branchDto: BranchDto) =
        Branch(
            name = branchDto.name,
            lastCommitSha = branchDto.commit?.sha
        )
}