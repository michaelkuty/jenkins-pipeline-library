#!/usr/bin/env groovy

@NonCPS
def call(String title, String commit_context, Closure body=null) {
    try {
        step([$class: 'GitHubCommitStatusSetter',
              contextSource: [$class: 'ManuallyEnteredCommitContextSource', context: commit_context],
              errorHandlers: [[$class: 'ShallowAnyErrorHandler']],
              statusResultSource: [$class: 'ConditionalStatusResultSource',
              results: [[$class: 'AnyBuildResult', message: "${title} - Build ${env.BUILD_NUMBER} Started", state: 'PENDING']]]
        ])
        if (body) { body() }
        step([$class: 'GitHubCommitStatusSetter',
              contextSource: [$class: 'ManuallyEnteredCommitContextSource', context: commit_context],
              errorHandlers: [[$class: 'ShallowAnyErrorHandler']],
              statusResultSource: [$class: 'ConditionalStatusResultSource',
              results: [[$class: 'BetterThanOrEqualBuildResult', message: "${title} - Build ${env.BUILD_NUMBER} Started", state: 'PENDING']]]
        ])
        step([$class: 'GitHubCommitStatusSetter',
              contextSource: [$class: 'ManuallyEnteredCommitContextSource', context: commit_context],
              errorHandlers: [[$class: 'ShallowAnyErrorHandler']],
              statusResultSource: [
                $class: 'ConditionalStatusResultSource',
                results: [
                    [$class: 'BetterThanOrEqualBuildResult', message: "${title} - Build ${env.BUILD_NUMBER} Succeeded", result: 'SUCCESS', state: 'SUCCESS'],
                ]
              ]
            ])
    } catch(e) {
        currentBuild.result = 'FAILED'
        step([$class: 'GitHubCommitStatusSetter',
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
        throw e
    }
}
