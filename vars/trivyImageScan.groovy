def call(Map config = [:]) {

    def svc  = config.get('svc_name', 'example-svc')
    sh """
    trivy image \
    --scanners vuln \
    --severity HIGH,CRITICAL \
    --ignore-unfixed \
    --format table \
    --exit-code 1 \
    --no-progress \
    ${svc}:${env.BUILD_NUMBER}
    """
}