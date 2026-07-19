def call(Map config = [:]) {

    def svc  = config.get('svc_name', 'example-svc')
    def exit_code = config.get('exit_code', '')
    sh """
    trivy image \
    --scanners vuln \
    --severity HIGH,CRITICAL \
    --ignore-unfixed \
    --format table \
    ${exit_code} \
    --no-progress \
    ${svc}:${env.BUILD_NUMBER}
    """
}