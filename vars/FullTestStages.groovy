def call(Map config) {
    if (config.PROJECT_MACHINE_LABEL == null){
        error "PROJECT_MACHINE_LABEL is null"
    }
    if (config.TEST_MACHINE_LABEL == null){
        error "TEST_MACHINE_LABEL is null"
    }
    if (config.BEEVO_TASKS_LINK == null){
        error "BEEVO_TASKS_LINK is null"
    }
    config.BEEVO_TASKS_LINK = "http://automatedtest." + config.BEEVO_PROJECT_NAME + ".env2.bsolus.pt" + config.BEEVO_TASKS_LINK
    pipeline {
        agent none
        stages{
            stage('Pre-install project'){
                agent {
                    node {
                        label "${config.PROJECT_MACHINE_LABEL}"
                    }
                }
                steps {
                    prepareProject( config )
                }
            }
            stage('Install project'){
                agent {
                    node {
                        label "${config.PROJECT_MACHINE_LABEL}"
                    }
                }
                steps{
                    beevoUpdateProject( config )
                }
            }
            stage('Run stress test'){
                agent {
                    node {
                        label "${config.TEST_MACHINE_LABEL}"
                    }
                }
                steps {
                    runLoadTest( config )
                }
            }
            /*
            stage('Run acceptance test'){
                agent {
                    node {
                        label "${config.PROJECT_MACHINE_LABEL}"
                    }
                }
                steps {
                    sh "date"
                }
            }*/
            stage('Remove project'){
                agent {
                    node {
                        label "${config.PROJECT_MACHINE_LABEL}"
                    }
                }
                steps {
                    clearProject( config )
                }
            }
        }
    }
/*       
    pipeline {
        parameters {
            string(name: 'gogo', defaultValue: 'Hello', description: 'How should I greet the world?')
        }
    }
*/
}