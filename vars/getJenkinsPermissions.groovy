#!/usr/bin/env groovy
import groovy.json.*
import com.saltstack.jenkins.JenkinsPerms

@NonCPS
def call() {
    return JenkinsPerms.toJSON()
}
