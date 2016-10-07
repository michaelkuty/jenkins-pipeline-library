#!/usr/bin/env groovy

import org.apache.commons.lang.StringEscapeUtils


def call(String build_status = 'SUCCESSFUL', String message, String channel) {
    build_status = build_status ?: 'SUCCESSFUL'

    // Default values
    def color_name = 'RED'
    def color_code = '#FF0000'
    def summary = "<b>${build_status}</b>: <a href='${env.BUILD_URL}'>${env.BUILD_DISPLAY_NAME}</a></br>${message}"

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
    slackSend(channel: channel, color: color_code, message: StringEscapeUtils.escapeHtml(summary))
}
