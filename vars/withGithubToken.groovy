

def call(String credentials_id, Closure body=null) {
    withCredentials([[variable: "GITHUB_TOKEN",
                     credentialsId: credentials_id,
                     $class: "StringBinding"]]) {
        if (body) {
            body()
        }
    }
}
