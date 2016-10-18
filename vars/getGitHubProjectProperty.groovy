#!/usr/bin/env groovy

import com.coravy.hudson.plugins.github.GithubProjectProperty

@NonCPS
def call() {
    return currentBuild.rawBuild.getProject().getProperty(GithubProjectProperty.class)
}
