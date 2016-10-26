#!/usr/bin/env groovy

def call() {
    repo = getAuthenticatedGitHubRepository()
    return [login: repo.root.login, token: repo.root.encodedAuthorization.split()[-1]]
}
