def call(Map vars, Closure body=null) {
    vars = vars ?: [:]
    node(vars.get("label", null)) {
        wrap([$class: 'TimestamperBuildWrapper']) {
            wrap([$class: 'AnsiColorBuildWrapper']) {
                if (body) { body() }
            }
        }
    }
}
