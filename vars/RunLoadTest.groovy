import org.apache.commons.lang.StringUtils

def call(
    String link,
    String result_dir
) {
    def today = new Date()
    today = today.format('yyyyMMddHmmss')
    result_dir = StringUtils.stripStart(result_dir, "/")
    result_dir = StringUtils.stripEnd(result_dir, "/")
    result_dir = "/${result_dir}/"
    echo """
        node generate-task.js -l "${link}"
        mkdir -p "${result_dir}"
        locust -f locust_main.py --no-web -c 30 -r10 -t 120s --print-stats --only-summary --csv="${result_dir}${today}"
    """
}