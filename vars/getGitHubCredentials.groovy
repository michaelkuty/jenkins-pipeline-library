#!/usr/bin/env groovy

@NonCPS
def call() {
    repo = getAuthenticatedGitHubRepository()
    return [login: repo.root.login, token: repo.root.encodedAuthorization.split()[-1]]
}
