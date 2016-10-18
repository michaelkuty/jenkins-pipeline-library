def call(Map vars, Closure body=null) {
    vars = vars ?: [:]
    node(vars.get("label", null)) {
        wrap([$class: 'TimestamperBuildWrapper']) {
            wrap([$class: 'AnsiColorBuildWrapper']) {
                currentBuild.result = 'PENDING'
                timeout_vars = vars.get('timeout', null)
                if ( timeout_vars ) {
                    timeout(time: timeout_vars.get('time'), unit: timeout_vars.get('unit', 'MINUTES')) {
                        try {
                            if (body) { body() }
                            currentBuild.result = 'SUCCESS'
                        } catch(InterruptedException ie) {
                            currentBuild.result = 'ABORTED'
                            throw e
                        } catch(e) {
                            currentBuild.result = 'FAILURE'
                            throw e
                        }
                    }
                } else {
                    try {
                        if (body) { body() }
                        currentBuild.result = 'SUCCESS'
                    } catch(InterruptedException ie) {
                        currentBuild.result = 'ABORTED'
                        throw e
                    } catch(e) {
                        currentBuild.result = 'FAILURE'
                        throw e
                    }
                }
            }
        }
    }
}
