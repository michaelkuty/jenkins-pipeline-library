#!/usr/bin/env groovy

def call() {
    repo = getAuthenticatedGitHubRepository()
    def branches = []
    if ( repo != null ) {
        repo.getBranches().each { branch_name, branch_data ->
            branches.add(branch_name)
        }
    }
    return branches
}
