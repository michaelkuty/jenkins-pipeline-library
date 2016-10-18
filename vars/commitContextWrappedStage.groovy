#!/usr/bin/env groovy

@NonCPS
def call(String title, String commit_context, Boolean set_pending=true, Closure body=null) {
    try {
        if ( set_pending ) {
            try {
                step([$class: 'GitHubCommitStatusSetter',
                      reposSource: [$class: 'ManuallyEnteredRepositorySource', url: getGitHubProjectProperty()],
                      contextSource: [$class: 'ManuallyEnteredCommitContextSource', context: commit_context],
                      errorHandlers: [[$class: 'ShallowAnyErrorHandler']],
                      statusResultSource: [
                        $class: 'ConditionalStatusResultSource',
                        results: [[$class: 'AnyBuildResult', message: "${title} - Build ${env.BUILD_NUMBER} Started", state: 'PENDING']]
                      ]
                ])
            } catch(ee1) {}
        }
        if (body) { body() }
        try {
            step([$class: 'GitHubCommitStatusSetter',
                  reposSource: [$class: 'ManuallyEnteredRepositorySource', url: getGitHubProjectProperty()],
                  contextSource: [$class: 'ManuallyEnteredCommitContextSource', context: commit_context],
                  errorHandlers: [[$class: 'ShallowAnyErrorHandler']],
                  statusResultSource: [
                    $class: 'ConditionalStatusResultSource',
                    results: [
                        [$class: 'BetterThanOrEqualBuildResult', message: "${title} - Build ${env.BUILD_NUMBER} Succeeded", result: 'SUCCESS', state: 'SUCCESS'],
                    ]
                  ]
                ])
            } catch(ee2) {}
    } catch(e) {
        currentBuild.result = 'FAILED'
        try {
            step([$class: 'GitHubCommitStatusSetter',
                  reposSource: [$class: 'ManuallyEnteredRepositorySource', url: getGitHubProjectProperty()],
                  contextSource: [$class: 'ManuallyEnteredCommitContextSource', context: commit_context],
                  errorHandlers: [[$class: 'ShallowAnyErrorHandler']],
                  statusResultSource: [
                    $class: 'ConditionalStatusResultSource',
                    results: [
                        [$class: 'BetterThanOrEqualBuildResult', message: "${title} - Build ${env.BUILD_NUMBER} Unstable", result: 'UNSTABLE', state: 'FAILURE'],
                        [$class: 'BetterThanOrEqualBuildResult', message: "${title} - Build ${env.BUILD_NUMBER} Error", result: 'FAILURE', state: 'ERROR']
                    ]
                  ]
                ])
            } catch(ee3) {}
        throw e
    }
}
