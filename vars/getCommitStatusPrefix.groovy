#!/usr/bin/env groovy

@NonCPS
def call() {
    return env.JOB_NAME.split('/')[0..-2].join('/')
}
