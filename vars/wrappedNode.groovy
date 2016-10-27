#!/usr/bin/env groovy

def call(Map vars, Closure body=null) {
    vars = vars ?: [:]
    node(vars.get("label", null)) {
        wrap([$class: 'TimestamperBuildWrapper']) {
            wrap([$class: 'AnsiColorBuildWrapper']) {
                timeout_vars = vars.get('timeout', null)
                if ( timeout_vars ) {
                    timeout(time: timeout_vars.get('time'), unit: timeout_vars.get('unit', 'MINUTES')) {
                        if (body) { body() }
                    }
                } else {
                    if (body) { body() }
                }
            }
        }
    }
}
