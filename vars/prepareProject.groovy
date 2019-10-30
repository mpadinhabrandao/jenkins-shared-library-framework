def call(
    String project_key, 
    String envi_project_key, 
    String envi_dir, 
    String db_prefix, 
    String db_ip, 
    String db_name, 
    String url
) {
    sh """
        /root/re-install/run.sh \
        --proj-name ${project_key} \
        --proj-env ${envi_project_key} \
        --envi ${envi_dir} \
        --mysql-db-prefix ${db_prefix} \
        --mysql-dump-ip ${db_ip} \
        --mysql-dump-name ${db_name} \
        --prod-url ${url} 
    """
}