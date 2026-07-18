def call(Map config = [:]) {
    sh 'gitleaks detect --source=. --verbose --report-path=gitleaks-report.json'
}