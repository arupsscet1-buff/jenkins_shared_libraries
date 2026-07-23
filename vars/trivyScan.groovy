def call(Map config = [:]) {
    sh 'trivy fs \
          --severity HIGH,CRITICAL \
          .'
}
