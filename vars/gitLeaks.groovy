def call(Map config = [:]) {
    def gitLeaks = "gitleaks detect --source="." --verbose --report-path=gitleaks-report.json"
    sh gitLeaks
}