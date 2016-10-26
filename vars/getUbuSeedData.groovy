#!/usr/bin/env groovy
import com.saltstack.jenkins.projects.Ubu

@NonCPS
def call() {
    return new Ubu().toJSON(include_branches = false, include_prs = true)
}
