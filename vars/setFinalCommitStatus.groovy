#!/usr/bin/env groovy

def call(String title, String commit_context, Closure body=null) {

    echo "Setting the final commit status"
    if (currentBuild.result == null) { // Build is ongoing
        state = 'PENDING'
        build_status = 'In Progress'
    } else if (currentBuild.result == 'SUCCESS') {
        state = 'SUCCESS'
        build_status = 'Success'
    } else if (currentBuild.result == 'FAILURE') {
        state = 'ERROR'
        build_status = 'Failed'
    } else if (currentBuild.result == 'UNSTABLE') {
        state = 'ERROR'
        build_status = 'Unstable'
    } else if (currentBuild.result == 'ABORTED') {
        state = 'ERROR'
        build_status = 'Aborted'
    }
    def commit_status_message = "Build ${build_status}"
    build_status = null
    setGitHubPullRequestStatus state: state, context: env.JOB_NAME, message: commit_status_message
}
