#!/usr/bin/env groovy

def call(Closure body=null) {
    try {
        if (body) { body() }
        currentBuild.result = 'SUCCESS'
        echo "Setting currentBuild.result to ${currentBuild.result}"
    } catch(InterruptedException ie) {
        currentBuild.result = 'ABORTED'
        echo "Setting currentBuild.result to ${currentBuild.result}"
        throw e
    } catch(e) {
        currentBuild.result = 'FAILURE'
        echo "Setting currentBuild.result to ${currentBuild.result}"
        throw e
    }
}
