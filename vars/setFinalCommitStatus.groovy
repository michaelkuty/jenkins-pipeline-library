

def call(String title, String commit_context, Closure body=null) {

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
    setGitHubPullRequestStatus state: state, context: env.JOB_NAME, message: "Build ${build_status}"
}
