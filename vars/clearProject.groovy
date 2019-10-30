def call(Map config) {
    if (config.BEEVO_PROJECT_NAME == null){
        error "BEEVO_PROJECT_NAME is null"
    }
    echo "clearProject is OK"
/*
    sh """
        /root/re-install/remove.sh \
        --site ${config.BEEVO_PROJECT_NAME} \
        --envi automatedtest
    """
*/
}