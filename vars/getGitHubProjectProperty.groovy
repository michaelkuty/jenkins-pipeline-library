#!/usr/bin/env groovy

import com.coravy.hudson.plugins.github.GithubProjectProperty


def call() {
    return currentBuild.rawBuild.getProject().getProperty(GithubProjectProperty.class)
}
