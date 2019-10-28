def call(String name) {
    pipeline {
        agent none

        parameters {
            string(name: 'gogo', defaultValue: 'Hello', description: 'How should I greet the world?')
        }

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
                    echo this.env.BEEVO_PROJECT_NAME
                    echo this.env.BEEVO_PROJECT_ENV
                    echo this.env.BEEVO_PROJECT_BD_PREFIX
                    echo this.env.BEEVO_PRODUCTION_IP
                    echo this.env.BEEVO_PRODUCTION_BDNAME
                    echo this.env.BEEVO_PRODUCTION_URL
                    echo this.env.BEEVO_PROJECT_NAME
                }
            }
            stage('Install project'){
                steps{
                    echo "/var/www/html/beevo/"+this.env.BEEVO_PROJECT_NAME+"/automatedtest/"
                    echo this.env.GIT_URL
                    echo this.env.GIT_BRANCH
                    echo this.env.GIT_CREDENTIALSID
                    echo "beevo update-project --repair --no-version --production"
                }
            }
            stage('Generate tasks files'){
                steps {
                    echo "node generate-task.js -l "+this.env.BEEVO_TASKS_FILE
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
                    echo "/root/re-install/remove.sh --site "+this.env.BEEVO_PROJECT_NAME+" --envi automatedtest"
                }
            }
        }
    }
}