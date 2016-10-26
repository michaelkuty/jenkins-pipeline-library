

def call() {
    repo = getAuthenticatedGitHubRepository()
    if ( repo != null ) {
        return repo.getDescription()
    }
    return null
}
