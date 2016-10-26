import com.saltstack.jenkins.projects.Ubu

def call() {
    return new Ubu().toJSON(include_branches = false, include_prs = true)
}
