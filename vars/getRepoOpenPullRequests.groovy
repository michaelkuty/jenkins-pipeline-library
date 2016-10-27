#!/usr/bin/env groovy

import hudson.Functions
import org.kohsuke.github.GitHub
import org.kohsuke.github.GHIssueState

@NonCPS
def call(String credentialsId, String repo, String) {
    withGithubToken(credentialsId) {
        def prs = []
        def gh = GitHub.connectUsingOAuth(env.GITHUB_TOKEN)
        def _repo = gh.getRepository(repo)
        _repo.getPullRequests(GHIssueState.OPEN).each { pr ->
            echo '  * Loading PR #' + pr.number
            prs.add([
                number: pr.number,
                title: pr.title,
                url: pr.getHtmlUrl(),
                body: pr.body,
                rendered_description: """
                    <h3>
                        <img src="${hudson.Functions.getResourcePath()}/plugin/github/logov3.png"/>
                        <a href="${pr.getHtmlUrl()}" title="${pr.title}" alt="${pr.title}">#${pr.number}</a>
                        &mdash;
                        ${pr.title}
                    <h3>
                    <br/>
                    ${renderGithubMarkupToHtml(credentialsId, repo, pr.body)}
                    """.stripIndent(),
                author_email: pr.getUser().getEmail()
            ])
        return prs
    }
}
