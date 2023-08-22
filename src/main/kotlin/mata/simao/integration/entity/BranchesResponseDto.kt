package mata.simao.integration.entity
data class BranchDto(val name: String? = null, val commit: BranchCommitDto? = null)
data class BranchCommitDto(val sha: String? = null)