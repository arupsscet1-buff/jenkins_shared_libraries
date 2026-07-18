def call(Map config = [:] {
    def trivyScan = "trivy fs --severity HIGH,CRITICAL ."
    sh trivyScan
}
