def call(Map config = [:]) {
    def port = config.get(port: '')
    def svc  = config.get(svc_name: '')

    sh 'docker build \
        -f docker/Dockerfile \
        --build-arg JAR_FILE=${svc}/target/${svc}*.jar \
        --build-arg EXPOSED_PORT=${port} \
        -t ${svc}:${BUILD_NUMBER} .'
}