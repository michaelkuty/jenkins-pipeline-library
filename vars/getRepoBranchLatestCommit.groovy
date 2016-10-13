import org.kohsuke.github.GitHub

def call(String credentialsId, String repo, String branch) {
    withGithubToken(credentialsId) {
        def gh = GitHub.connectUsingOAuth(env.GITHUB_TOKEN)
        def repo = gh.getRepository(repo)
        def branch = repo.getBranch(branch)
        return branch.getSHA1()
    }
}
