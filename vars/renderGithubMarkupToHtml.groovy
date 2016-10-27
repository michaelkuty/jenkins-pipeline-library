#!/usr/bin/env groovy

import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType
import static groovyx.net.http.Method.*


@NonCPS
def call(String credentialsId, String repo, String text) {
    withGithubToken(credentialsId) {
        def http = new HTTPBuilder('https://api.github.com')
        http.request(POST, ContentType.TEXT) { req ->
            uri.path = '/markdown'
            headers.'User-Agent' = 'Mozilla/5.0'
            headers.'Accept' = 'application/json'
            headers.'Authorization' = "Bearer ${env.GITHUB_TOKEN}"
            requestContentType = ContentType.JSON
            body = [
                text: text,
                mode: 'gfm',
                context: repo
            ]
            response.success = { resp, reader ->
                if ( reader != null ) {
                    return reader.text
                }
                return ''
            }
        }
    }
}
