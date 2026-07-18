def call(Map config = [:]) {
    //Define defaults
    def goal = config.get('goal', 'clean package')
    def projectList = config.get('projectList', '')
    def skipTests = config.get('skipTests', false)
    def sonarServer = config.get('sonarServer', 'SonarQube-Server')
    def runSonar = config.get('runSonar', true)
    // Construct optional project flag suffix
    def plFlag = projectList ? "-pl ${projectList}" : ""

    //Enforce company rules
    if(skipTests && env.BRANCH_NAME == 'main') {
        echo "Warning: Overriding skipTests. Tests cannot be skipped on main branch"
        skipTests = false
    }
    else {
        echo "Branch is '${env.BRANCH_NAME}'. Respecting developer choice: skipTests = ${skipTests}"
    }

    // ==========================================
    // STAGE 1: Clean, Compile, and Test
    // ==========================================
    // Combined clean, compile, test, and jacoco execution into ONE highly optimized command string.

    def mvnBuildCmd = "./mvnw clean verify ${plFlag}"

    if (skipTests) {
        mvnBuildCmd += " -DskipTests"
    }
    // The ternary shortcut combines string generation and condition checking in one line!
    // def mvnBuildCmd = "./mvnw clean verify ${plFlag}" + (skipTests ? " -DskipTests" : "")

    echo "Running Application Build Pipeline..."
    sh mvnBuildCmd


    // ==========================================
    // STAGE 2: Conditional SonarQube Scanning
    // ==========================================
    // This executes right after the build completes in the same workspace directory.
    if (runSonar && !skipTests) {
        echo "Executing SonarQube Code Quality Analysis..."
        
        withSonarQubeEnv(sonarServer) {
            def sonarCmd = "./mvnw org.sonarsource.scanner.maven:sonar-maven-plugin:sonar ${plFlag} " +
                           "-Dsonar.qualitygate.wait=true " +
                           "-Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml"
            sh sonarCmd
        }
    } else if (skipTests) {
        echo "Skipping SonarQube analysis because tests were skipped."
    }

    // ==========================================
    // STAGE 3: Package build
    // ==========================================
    def mvnPackage = "./mvnw package -DskipTests ${plFlag}"
    echo "Packaging final artifact..."
    sh mvnPackage


}