def call(Map config = [:]) {
    //set default parameters if parameters are omitted
    def branch = config.get('branch', 'main')
    def credentials = config.get('credentialsId', 'github_cred')
    def repourl = config.get('url')

    //Fail fast if repourl not provided
    if(!repourl) {
        error "git checkout error, 'url' parameter is mandatory!"
    }

    echo "Skipping manual config: Checking out branch '${branch}' from ${repourl}"

    //Native jenkins github plugin step
    checkout scmGit(
        branches: [[name: branch]],
        userRemoteConfigs: [[credentialsId: credentials, url: repourl]]
    )
}
