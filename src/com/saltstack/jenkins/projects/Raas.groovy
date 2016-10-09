package com.saltstack.jenkins.projects

import com.saltstack.jenkins.Project


class Raas extends Project {

    Raas() {
        super()
        this.name = 'raas'
        this.display_name = 'RaaS'
        this.repo = 'saltstack/raas'
    }
}
