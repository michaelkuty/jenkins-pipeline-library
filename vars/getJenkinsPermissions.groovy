import hudson.security.Permission

@NonCPS
def call(String context) {
    def available = hudson.security.Permission.getAll().collect { perm -> perm.getId() } as Set

    if ( context == 'project' ) {
        return [
            'com.cloudbees.plugins.credentials.CredentialsProvider.Create',
            'com.cloudbees.plugins.credentials.CredentialsProvider.Delete',
            'com.cloudbees.plugins.credentials.CredentialsProvider.ManageDomains',
            'com.cloudbees.plugins.credentials.CredentialsProvider.Update',
            'com.cloudbees.plugins.credentials.CredentialsProvider.View',
            'hudson.model.Item.Build',
            'hudson.model.Item.Cancel',
            'hudson.model.Item.Configure',
            'hudson.model.Item.Delete',
            'hudson.model.Item.Discover',
            'hudson.model.Item.Move',
            'hudson.model.Item.Read',
            'hudson.model.Item.Update',
            'hudson.model.Item.ViewStatus',
            'hudson.model.Item.Workspace',
            'hudson.model.Run.Delete',
            'hudson.model.Run.Update',
            'hudson.scm.SCM.Tag'
        ].findAll { permission -> available.contains(permission) } as Set
    }

    if ( context == 'folder' ) {
        return [
            'hudson.model.Item.Build',
            'hudson.model.Item.Cancel',
            'hudson.model.Item.Configure',
            'hudson.model.Item.Create',
            'hudson.model.Item.Delete',
            'hudson.model.Item.Discover',
            'hudson.model.Item.Move',
            'hudson.model.Item.Read',
            'hudson.model.Item.Workspace',
            'hudson.model.Run.Delete',
            'hudson.model.Run.Update',
        ].findAll { permission -> available.contains(permission) } as Set
    }

    return available
}
