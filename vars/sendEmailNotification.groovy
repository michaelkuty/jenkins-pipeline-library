#!/usr/bin/env groovy


@NonCPS
def call() {
    def color_name
    def color_code
    def build_status

    if (currentBuild.result == null) { // Build is ongoing
        currentBuild.result == 'PENDING'
    }

    if (currentBuild.result == 'PENDING') { // Build is ongoing
        build_status = 'In Progress'
        color_name = 'YELLOW'
        color_code = '#FFFF00'
    } else if (currentBuild.result == 'SUCCESS') {
        build_status = 'Success'
        color_name = 'GREEN'
        color_code = '#00FF00'
    } else if (currentBuild.result == 'FAILURE') {
        build_status = 'Failed'
        color_name = 'RED'
        color_code = '#FF0000'
    } else if (currentBuild.result == 'UNSTABLE') {
        build_status = 'Unstable'
        color_name = 'YELLOW'
        color_code = '#FFFF00'
    } else if (currentBuild.result == 'ABORTED') {
        build_status = 'Aborted'
        color_name = 'RED'
        color_code = '#FF0000'
    }

    def full_display_name = currentBuild.getFullDisplayName()
    def subject = "${build_status}: ${full_display_name}"
    def body = """<p><span style="color: ${color_code}">${build_status}</span>: ${full_display_name}:</p>
    <p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${full_display_name}</a>&QUOT;</p>"""

    // Send an email notification!
    emailext(attachLog: false,
             body: body,
             recipientProviders: [[$class: 'CulpritsRecipientProvider'],
                                  [$class: 'FirstFailingBuildSuspectsRecipientProvider'],
                                  [$class: 'RequesterRecipientProvider']],
             subject: subject)
}
