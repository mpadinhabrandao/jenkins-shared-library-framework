def call(Map config) {
    if (config.BEEVO_TASKS_LINK == null){
        error "BEEVO_TASKS_LINK is null"
    }
    if (config.BEEVO_PROJECT_NAME == null){
        error "BEEVO_PROJECT_NAME is null"
    }
    if (config.GIT_CREDENTIALSID == null){
        error "GIT_CREDENTIALSID is null"
    }
    if (config.GIT_URL == null){
        error "GIT_URL is null"
    }
    
    dir("/var/code/"){
        sh """
        node generate-task.js -l "${config.BEEVO_TASKS_LINK}"
        """
    }
    dir("/var/code/"){
        sh """
        TODAY=`date +"%Y%m%d%H%M%S"`
        RESULTDIR="/var/log/locust/results/${config.BEEVO_PROJECT_NAME}/automatedtest/"
        mkdir -p "\${RESULTDIR}"
        
        locust -f locust_main.py --no-web -c 30 -r10 -t 120s --print-stats --only-summary --csv="\${RESULTDIR}\${TODAY}"
        """
    }
}