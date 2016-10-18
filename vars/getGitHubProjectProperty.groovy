#!/usr/bin/env groovy

import com.coravy.hudson.plugins.github.GithubProjectProperty


def call() {
    return currentBuild.getProject().getProperty(GithubProjectProperty.class)
}
