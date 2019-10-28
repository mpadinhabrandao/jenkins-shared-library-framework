def call(Map config) {
    pipeline {
        agent none

/*       
         parameters {
            string(name: 'gogo', defaultValue: 'Hello', description: 'How should I greet the world?')
        }
*/

        environment{
            GIT_URL="https://bitbucket.org/bsolus_daredevil/delta.git"
            GIT_BRANCH="production"
            GIT_CREDENTIALSID="Bitbucket-Carrola-2018"
            BEEVO_PROJECT_NAME = "delta"
            BEEVO_PROJECT_ENV = "production"
            BEEVO_PROJECT_BD_PREFIX = "dlt_"
            BEEVO_PRODUCTION_IP = "80.172.253.170"
            BEEVO_PRODUCTION_BDNAME = "deltaq_site"
            BEEVO_PRODUCTION_URL = "https://pt.mydeltaq.com"
            BEEVO_TASKS_FILE = "http://automatedtest.${BEEVO_PROJECT_NAME}.env2.bsolus.pt/pt/pt/task/latest/json/seo-manager/Tests/generateLoadTests"
            
        }
        
        stages{
            stage('Pre-install project'){
                steps {
                    echo "name is ${config.name}"
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
}