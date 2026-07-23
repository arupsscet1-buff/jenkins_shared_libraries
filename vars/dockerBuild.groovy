def call(Map config = [:]) {
    def port = config.get('port', '8080')
    def svc  = config.get('svc_name', 'example-svc')

   // def version = sh(
     //   script: 'mvn help:evaluate -Dexpression=project.version -q -DforceStdout',
       // returnStdout: true
    //).trim()

    echo "Version: 4.0.1"

    sh """
    docker build \
    -f docker/Dockerfile \
    --build-arg ARTIFACT_NAME=${svc}/target/${svc}-4.0.1 \
    --build-arg EXPOSED_PORT=${port} \
    -t ${svc}:${env.BUILD_NUMBER} .
    """
}
