import org.kohsuke.github.GitHub

@NonCPS
def call(String credentialsId, String repo, String team_name) {
    withGithubToken(credentialsId) {
        def gh = GitHub.connectUsingOAuth(env.GITHUB_TOKEN)
        def _repo = gh.getRepository(repo)
        def teams = _repo.getTeams()
        def usernames = []
        teams.each { team ->
            if ( team.getName() == team_name ) {
                team.getMembers().each { user ->
                    usernames.add(user.login)
                }
            }
        }
        return usernames
    }
}
