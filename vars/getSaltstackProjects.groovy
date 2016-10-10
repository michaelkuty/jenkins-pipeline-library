import com.saltstack.jenkins.Projects


def call(Map options) {
    options = options ?: [:]
    def include_branches = options.get("include_branches", false)
    def include_prs = options.get("include_prs")
    return Projects.toJSON(include_branches = true, include_prs = false)
}
