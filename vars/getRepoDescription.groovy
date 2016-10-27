#!/usr/bin/env groovy

import org.kohsuke.github.GitHub

@NonCPS
def call(String credentialsId, String repo) {
    withGithubToken(credentialsId) {
        def gh = GitHub.connectUsingOAuth(env.GITHUB_TOKEN)
        def _repo = gh.getRepository(repo)
        return _repo.getDescription()
    }
}
