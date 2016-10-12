#!/usr/bin/env groovy


def call(String build_status = 'SUCCESSFUL') {
    build_status = build_status ?: 'SUCCESSFUL'

    // Default values
    def color_code = '#FF0000'
    def full_display_name = currentBuild.getFullDisplayName()
    def subject = "${build_status}: ${full_display_name}"

    // Override default values based on build status
    if (build_status == 'UNSTABLE') {
        color_code = '#FFFF00'
    } else if (build_status == 'SUCCESSFUL') {
        color_code = '#00FF00'
    } else {
        color_code = '#FF0000'
    }

    def body = """<p><span style="color: ${color_code}">${build_status}</span>: ${full_display_name}:</p>
    <p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${full_display_name}</a>&QUOT;</p>"""

    // Send an email notification!
    emailext(attachLog: true,
             body: body,
             recipientProviders: [[$class: 'CulpritsRecipientProvider'],
                                  [$class: 'FirstFailingBuildSuspectsRecipientProvider'],
                                  [$class: 'RequesterRecipientProvider']],
             subject: subject)
}
