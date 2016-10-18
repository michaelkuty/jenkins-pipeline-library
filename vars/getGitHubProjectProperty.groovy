#!/usr/bin/env groovy

import com.coravy.hudson.plugins.github.GithubProjectProperty


def call() {
    return currentbuild.getProject().getProperty(GithubProjectProperty.class)
}
