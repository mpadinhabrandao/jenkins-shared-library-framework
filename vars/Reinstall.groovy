def call(Map config) {
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