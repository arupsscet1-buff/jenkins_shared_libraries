def Call(Map config = [:]) {
    //Define defaults
    def goal = config.get('goal', 'compile package')
    def projectList = config.get('-projectList', 'src')
    def skipTests = config.get('skipTests', false)

    //Enforce company rules
    if(skipTests && env.BRANCH_NAME == 'main') {
        echo "Warning: Overriding skipTests. Tests cannot be skipped on main branch"
        skipTest = false

    //Construct the dynamic command
    def mvnCmd = "./mvnw -pl ${projectList} ${goal} "
    if(skipTests) {
        mvnCmd+= " -DskipTests"
    }

    //Execute using Jenkins native tool wrappers
    echo "Execution ${mvnCmd}"
    sh mvnCmd
    }

}