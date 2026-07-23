def call(Map config = [:]) {
    sh 'trivy fs \
          --offline-scan \
          --severity HIGH,CRITICAL \
          .'
}
