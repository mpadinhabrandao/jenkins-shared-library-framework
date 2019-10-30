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

    String directory = "/var/www/html/beevo/${config.BEEVO_PROJECT_NAME}/automatedtest/"

    pipeline {
        agent none
        stages{
            // stage('Pre-install project'){
            //     agent {
            //         node {
            //             label "${config.PROJECT_MACHINE_LABEL}"
            //         }
            //     }
            //     steps {
            //         prepareProject( 
            //             config.BEEVO_PROJECT_NAME, 
            //             config.BEEVO_PROJECT_ENV, 
            //             "automatedtest", 
            //             config.BEEVO_PROJECT_BD_PREFIX, 
            //             config.BEEVO_PRODUCTION_IP, 
            //             config.BEEVO_PRODUCTION_BDNAME, 
            //             config.url
            //         )
            //     }
            // }
            // stage('Install project'){
            //     agent {
            //         node {
            //             label "${config.PROJECT_MACHINE_LABEL}"
            //         }
            //     }
            //     steps{
            //         dir( "${directory}" ){
            //             git branch: "${config.GIT_BRANCH}", credentialsId: "${config.GIT_CREDENTIALSID}", url: "${config.GIT_URL}"
            //             beevoUpdateProject( ["--repair", "no-version", "--production"] )
            //             sh "chmod -R 755 . "
            //             sh "chown -R nginx:nginx . "
            //         }
            //     }
            // }
            stage('Run stress test'){
                // agent {
                //     node {
                //         label "${config.TEST_MACHINE_LABEL}"
                //     }
                // }
                steps {
                    //dir("/var/code/"){
                        runLoadTest( 
                            config.BEEVO_TASKS_LINK, 
                            "/var/log/locust/results/${config.BEEVO_PROJECT_NAME}/automatedtest/"
                        )
                    //}
                }
            }
            // stage('Run acceptance test'){
            //     agent {
            //         node {
            //             label "${config.PROJECT_MACHINE_LABEL}"
            //         }
            //     }
            //     steps {
            //         sh "date"
            //     }
            // }
            stage('Remove project'){
                // agent {
                //     node {
                //         label "${config.PROJECT_MACHINE_LABEL}"
                //     }
                // }
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