def call(Map config) {
    if (config.BEEVO_PROJECT_NAME == null){
        error "BEEVO_PROJECT_NAME is null"
    }
    if (config.GIT_BRANCH == null){
        error "GIT_BRANCH is null"
    }
    if (config.GIT_CREDENTIALSID == null){
        error "GIT_CREDENTIALSID is null"
    }
    if (config.GIT_URL == null){
        error "GIT_URL is null"
    }
    
    dir( "/var/www/html/beevo/${config.BEEVO_PROJECT_NAME}/automatedtest/" ){
        git branch: "${config.GIT_BRANCH}", credentialsId: "${config.GIT_CREDENTIALSID}", url: "${config.GIT_URL}"
    }
    dir( "/var/www/html/beevo/${config.BEEVO_PROJECT_NAME}/automatedtest/"){
        sh '''
        #Install project
        beevo update-project --repair --no-version --production

        chmod -R 777 .
        chown -R root:root .
        '''
    }
}