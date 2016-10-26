import org.kohsuke.github.GitHub

@NonCPS
def call(String credentialsId, String repo, String branch) {
    withGithubToken(credentialsId) {
        def gh = GitHub.connectUsingOAuth(env.GITHUB_TOKEN)
        def _repo = gh.getRepository(repo)
        def _branch = _repo.getBranch(branch)
        def sha1 = "${_branch.getSHA1()}"
        return sha1
    }
}
