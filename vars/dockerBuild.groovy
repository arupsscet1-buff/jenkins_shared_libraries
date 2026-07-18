def call(Map config = [:]) {
    def port = config.get('port': '')
    def svc  = config.get('svc_name': '')

    def version = sh(
        script: 'mvn help:evaluate -Dexpression=project.version -q -DforceStdout',
        returnStdout: true
    ).trim()

    echo "Version: ${version}"

    sh """
    docker build \
    -f docker/Dockerfile \
    --build-arg ARTIFACT_NAME=${svc}/target/${svc}-${version}*.jar \
    --build-arg EXPOSED_PORT=${port} \
    -t ${svc}:${BUILD_NUMBER} .
    """
}