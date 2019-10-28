def call(Map config) {
    pipeline {
        agent none
        stages{
            stage('Pre-install project'){
                when {
                    allOf {
                        expression { config.PROJECT_MACHINE_LABEL != null }
                        expression { config.TEST_MACHINE_LABEL != null }
                    }
                }
                agent {
                    node {
                        label "${config.PROJECT_MACHINE_LABEL}"
                    }
                }
                steps {
                    Reinstall( config )
                }
            }
            /*stage('Install project'){
                agent {
                    node {
                        label '80.172.253.177 (staging V3)'
                    }
                }
                steps{
                    
                    dir( "/var/www/html/beevo/$BEEVO_PROJECT_NAME/automatedtest/" ){
                        git branch: "${GIT_BRANCH}", credentialsId: "${GIT_CREDENTIALSID}", url: "${GIT_URL}"
                    }
                    dir( "/var/www/html/beevo/${BEEVO_PROJECT_NAME}/automatedtest/"){
                        sh '''
                        #Install project
                        beevo update-project --repair --no-version --production
        
                        chmod -R 777 .
                        chown -R root:root .
                        '''
                    }
                    
                }
            }
            stage('Generate tasks files'){
                agent {
                    node {
                        label '80.172.253.149'
                    }
                }
                steps {
                    dir("/var/code/"){
                        sh '''
                        node generate-task.js -l "${BEEVO_TASKS_FILE}"
                        '''
                    }
                }
            }
            stage('Run stress test'){
                agent {
                    node {
                        label '80.172.253.149'
                    }
                }
                steps {
                    dir("/var/code/"){
                        sh '''
                        TODAY=`date +"%Y%m%d%H%M%S"`
                        RESULTDIR="/var/log/locust/results/${BEEVO_PROJECT_NAME}/automatedtest/"
                        mkdir -p "${RESULTDIR}"
                        
                        locust -f locust_main.py --no-web -c 30 -r10 -t 120s --print-stats --only-summary --csv="${RESULTDIR}${TODAY}"
                        '''
                    }
                }
                post { 
                    failure {
                        steps {
                            sh 'date'
                        }
                    }
                }
            }
            stage('Run acceptance test'){
                agent {
                    node {
                        label '80.172.253.177 (staging V3)'
                    }
                }
                steps {
                    sh 'date'
                }
            }
            stage('Remove project'){
                agent {
                    node {
                        label '80.172.253.177 (staging V3)'
                    }
                }
                steps {
                    sh '''
                    /root/re-install/remove.sh \
                        --site $BEEVO_PROJECT_NAME \
                        --envi automatedtest
                    '''
                }
            }*/
        }
    }
/*       
    pipeline {
        agent none
        parameters {
            string(name: 'gogo', defaultValue: 'Hello', description: 'How should I greet the world?')
        }
        
        stages{
            stage('Pre-install project'){
                steps {
                    echo config.BEEVO_PROJECT_NAME
                    echo config.BEEVO_PROJECT_ENV
                    echo config.BEEVO_PROJECT_BD_PREFIX
                    echo config.BEEVO_PRODUCTION_IP
                    echo config.BEEVO_PRODUCTION_BDNAME
                    echo config.BEEVO_PRODUCTION_URL
                    echo config.BEEVO_PROJECT_NAME
                }
            }
            stage('Install project'){
                steps{
                    echo "/var/www/html/beevo/"+config.BEEVO_PROJECT_NAME+"/automatedtest/"
                    echo config.GIT_URL
                    echo config.GIT_BRANCH
                    echo config.GIT_CREDENTIALSID
                    echo "beevo update-project --repair --no-version --production"
                }
            }
            stage('Generate tasks files'){
                steps {
                    echo "node generate-task.js -l "+config.BEEVO_TASKS_FILE
                }
            }
            stage('Run stress test'){
                steps {
                    echo "locust -f locust_main.py --no-web -c 30 -r10 -t 120s --print-stats --only-summary"
                }
            }
            stage('Run acceptance test'){
                steps {
                    echo "Run acceptance test"
                }
            }
            stage('Remove project'){
                steps {
                    echo "/root/re-install/remove.sh --site "+config.BEEVO_PROJECT_NAME+" --envi automatedtest"
                }
            }
        }
    }
*/
}