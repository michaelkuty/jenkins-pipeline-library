#!/usr/bin/env groovy

// https://github.com/jenkinsci/slack-plugin/blob/master/src/main/java/jenkins/plugins/slack/ActiveNotifier.java

def call(String build_status = 'SUCCESSFUL', String channel = null) {
    build_status = build_status ?: 'SUCCESSFUL'

    // Default values
    def color_name = 'RED'
    def color_code = '#FF0000'
    def full_display_name = currentBuild.getFullDisplayName()
    def summary = "*${build_status}*:\n  <${env.BUILD_URL}|${full_display_name}>"

    // Override default values based on build status
    if (build_status == 'UNSTABLE') {
        color_name = 'YELLOW'
        color_code = '#FFFF00'
    } else if (build_status == 'SUCCESSFUL') {
        color_name = 'GREEN'
        color_code = '#00FF00'
    } else {
        color_name = 'RED'
        color_code = '#FF0000'
    }

    if ( channel != null ) {
        // Make sure channel starts with a #
        channel = (channel.startsWith('#')) ? channel : "#${channel}"
    }

    // Send a slack notification!
    slackSend(channel: channel, color: color_code, message: summary)
}
