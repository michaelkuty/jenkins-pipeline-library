#!/usr/bin/env groovy

def call() {

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
    setGitHubPullRequestStatus state: state, message: "Build ${build_status}"
}
