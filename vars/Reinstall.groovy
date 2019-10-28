def call(Map config) {
    if (config.BEEVO_PROJECT_NAME == null){
        error "BEEVO_PROJECT_NAME is null"
    }
    if (config.BEEVO_PROJECT_ENV == null){
        error "BEEVO_PROJECT_ENV is null"
    }
    if (config.BEEVO_PROJECT_BD_PREFIX == null){
        error "BEEVO_PROJECT_BD_PREFIX is null"
    }
    if (config.BEEVO_PRODUCTION_IP == null){
        error "BEEVO_PRODUCTION_IP is null"
    }
    if (config.BEEVO_PRODUCTION_BDNAME == null){
        error "BEEVO_PRODUCTION_BDNAME is null"
    }
    if (config.BEEVO_PRODUCTION_URL == null){
        error "BEEVO_PRODUCTION_URL is null"
    }
    sh """
            /root/re-install/run.sh \
            --proj-name ${config.BEEVO_PROJECT_NAME} \
            --proj-env ${config.BEEVO_PROJECT_ENV} \
            --envi automatedtest \
            --mysql-db-prefix ${config.BEEVO_PROJECT_BD_PREFIX} \
            --mysql-dump-ip ${config.BEEVO_PRODUCTION_IP} \
            --mysql-dump-name ${config.BEEVO_PRODUCTION_BDNAME} \
            --prod-url ${config.BEEVO_PRODUCTION_URL} 
        """
}