#!/usr/bin/env groovy

import com.cloudbees.jenkins.GitHubRepositoryName
import com.coravy.hudson.plugins.github.GithubProjectProperty


def call(String repo = null) {
    if ( repo == null ) {
        repo = currentbuild.getProject().getProperty(GithubProjectProperty.class)
    } else if ( repo.startsWith('https') == false ) {
        repo = "https://github.com/${repo}"
    }
    return GitHubRepositoryName.create(repo).resolve().iterator().next()
}
