#!/usr/bin/env groovy

@NonCPS
def call() {
    if (env.GIT_COMMIT == null ) {
        env.GIT_COMMIT = sh(returnStdout: true, script: 'git rev-parse HEAD').trim()
    }
    return env.GIT_COMMIT
}
