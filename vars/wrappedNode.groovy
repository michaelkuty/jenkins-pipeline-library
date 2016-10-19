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
                            echo "Setting currentBuild.result to ${currentBuild.result}"
                        } catch(InterruptedException ie) {
                            currentBuild.result = 'ABORTED'
                            echo "Setting currentBuild.result to ${currentBuild.result}"
                            throw e
                        } catch(e) {
                            currentBuild.result = 'FAILURE'
                            echo "Setting currentBuild.result to ${currentBuild.result}"
                            throw e
                        } finally {
                            on_finished_build = vars.get('on_finished_build', null)
                            if ( on_finished_build != null ) {
                                on_finished_build()
                            }
                        }
                    }
                } else {
                    try {
                        if (body) { body() }
                        currentBuild.result = 'SUCCESS'
                        echo "Setting currentBuild.result to ${currentBuild.result}"
                    } catch(InterruptedException ie) {
                        currentBuild.result = 'ABORTED'
                        echo "Setting currentBuild.result to ${currentBuild.result}"
                        throw e
                    } catch(e) {
                        currentBuild.result = 'FAILURE'
                        echo "Setting currentBuild.result to ${currentBuild.result}"
                        throw e
                    } finally {
                        on_finished_build = vars.get('on_finished_build', null)
                        if ( on_finished_build != null ) {
                            on_finished_build()
                        }
                    }
                }
            }
        }
    }
}
