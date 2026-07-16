def Call(Map config = [:]) {
    //Define defaults
    def goal = config.get('goal', 'clean package')
    def mavenTool = config.get('mavenVersion', 'M3')
    def jdkTool = config.get('jdkTool', 'Java21')
    def skipTests = config.get('skipTests', false)

    // 2. Enforce company rules
    if(skipTests && env.BRANCH_NAME == 'main') {
        echo "Warning: Overriding skipTests. Tests cannot be skipped on main branch"
        skipTest = false
    }

    // 3. Construct the dynamic command
    def mvnCmd = "mvn ${goal}"
    if(skipTests) {
        mvnCmd+= " -DskipTests"
    }

    // 4. Execute using Jenkins native tool wrappers
    // This automatically installs and paths the correct Java/Maven versions on the agent
    withEnv(["PATH+MAVEN=${tool mavenTool}/bin"]) {
        withEnv(["PATH+JDK=${tool jdkTool}/bin"]) {
            echo "Executing: ${mvnCmd} using ${mavenTool} and ${jdkTool}"
            sh mvnCmd
        }
    }

}