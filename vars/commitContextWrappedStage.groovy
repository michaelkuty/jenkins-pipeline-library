#!/usr/bin/env groovy

@NonCPS
def call(String title, String commit_context, Closure body=null) {
    try {
        setGitHubPullRequestStatus state: 'PENDING', context: commit_context, message: "${title} - Started"
        if (body) { body() }
        setGitHubPullRequestStatus state: 'SUCCESS', context: commit_context, message: "${title} - Success"
    } catch(e) {
        setGitHubPullRequestStatus state: 'ERROR', context: commit_context, message: "${title} - Error"
        throw e
    }
}
