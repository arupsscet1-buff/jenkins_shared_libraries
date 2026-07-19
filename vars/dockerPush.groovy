def call(Map config = [:]) {

    def svc = config.get('svc_name', 'example-svc')
    def registry = config.get('registry', 'docker.io')
    def repository = config.get('repository', 'arupsscet1')

    withCredentials([
        usernamePassword(
            credentialsId: 'docker-cred',
            usernameVariable: 'DOCKER_USER',
            passwordVariable: 'DOCKER_PASS'
        )
    ]) {

        sh """
            echo "\$DOCKER_PASS" | docker login ${registry} \
                --username "\$DOCKER_USER" \
                --password-stdin

            docker tag ${svc}:${env.BUILD_NUMBER} \
                ${repository}/${svc}:${env.BUILD_NUMBER}

            docker push ${repository}/${svc}:${env.BUILD_NUMBER}

            docker logout ${registry}
        """
    }
}