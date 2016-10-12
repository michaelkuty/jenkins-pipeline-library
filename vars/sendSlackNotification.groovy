#!/usr/bin/env groovy

// https://github.com/jenkinsci/slack-plugin/blob/master/src/main/java/jenkins/plugins/slack/ActiveNotifier.java
import hudson.model.Result


def call(String channel = null) {
    def color_name
    def color_code
    def build_status

    def result = currentBuild.getResult()

    if (result == null) { // Build is ongoing
        build_status = 'In Progress'
        color_name = 'YELLOW'
        color_code = '#FFFF00'
    } else if (result.isBetterOrEqualTo(Result.SUCCESS)) {
        build_status = 'Success'
        color_name = 'GREEN'
        color_code = '#00FF00'
    } else if (result.isBetterOrEqualTo(Result.FAILURE)) {
        build_status = 'Failed'
        color_name = 'RED'
        color_code = '#FF0000'
    } else if (result.isBetterOrEqualTo(Result.UNSTABLE)) {
        build_status = 'Unstable'
        color_name = 'YELLOW'
        color_code = '#FFFF00'
    } else {
        build_status = 'Error'
        color_name = 'RED'
        color_code = '#FF0000'
    }

    if ( channel != null ) {
        // Make sure channel starts with a #
        channel = (channel.startsWith('#')) ? channel : "#${channel}"
    }

    def full_display_name = currentBuild.getFullDisplayName()
    def summary = "*${build_status}*:\n  <${env.BUILD_URL}|${full_display_name}>"

    // Send a slack notification!
    slackSend(channel: channel, color: color_code, message: summary)
}
