#!/usr/bin/env groovy

import com.coravy.hudson.plugins.github.GithubProjectProperty

@NonCPS
def call() {
    return currentBuild.rawBuild.getParent().getProperty(GithubProjectProperty.class)
}
