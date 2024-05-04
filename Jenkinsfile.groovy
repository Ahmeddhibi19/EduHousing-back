def project_token = 'abcdefghijklmnopqrstuvwxyz0123456789ABCDEF'

properties([
        gitLabConnection('your-gitlab-connection-name'),
        pipelineTriggers([
                [
                        $class: 'GitLabPushTrigger',
                        branchFilterType: 'All',
                        triggerOnPush: true,
                        triggerOnMergeRequest: true,
                        triggerOpenMergeRequestOnPush: "never",
                        triggerOnNoteRequest: true,
                        noteRegex: "Jenkins please retry a build",
                        skipWorkInProgressMergeRequest: true,
                        secretToken: project_token,
                        ciSkip: false,
                        setBuildDescription: true,
                        addNoteOnMergeRequest: true,
                        addCiMessage: true,
                        addVoteOnMergeRequest: true,
                        acceptMergeRequestOnSuccess: true,
                        branchFilterType: "NameBasedFilter",
                        includeBranchesSpec: "",
                        excludeBranchesSpec: "",
                ]
        ])
])


node(){
    try{
        def buildNum = env.BUILD_NUMBER
        def branchName= env.BRANCH_NAME

        print buildNum
        print branchName

        stage('Env - clone generator'){
            git "http://gitlab.example.com/pipeline/generator.git"
        }

        stage('Env - run mysql'){
            sh "chmod +x generator.sh"
            sh "./generator.sh -m"
            sh "docker ps -a"

        }
        stage('Env - run mongo'){
            sh "chmod +x generator.sh"
            sh "./generator.sh -mn"
            sh "docker ps -a"

        }
        /*get the app*/
        stage('SERVICE - Git checkout'){
            git branch: branchName, url:"http://gitlab.example.com/pipeline/app.git"
        }
        if(branchName =="dev"){
            extension ="-SNAPSHOT"
        }
        if(branchName == "stage"){
            extension = "-RC"
        }
        if(branchName == "master"){
            extension = ""
        }
        /*Retrieving the long commitID*/
        def commitIdLong = sh returnStdout: true, script: 'git rev-parse HEAD'

        /*Retrieving the short commitID*/
        def commitId = commitIdLong.take(7)

        /*Changing the version in the pom.xml*/
        sh "sed -i s/'-SNAPSHOT'/${extension}/g pom.xml"

        /* Récupération de la version du pom.xml après modification */
        def version = sh returnStdout: true, script: "cat pom.xml | grep -A1 '<artifactId>EduHousing' | tail -1 |perl -nle 'm{.*<version>(.*)</version>.*};print \$1' | tr -d '\n'"

        print """
         #################################################
            BanchName: $branchName
            CommitID: $commitId
            AppVersion: $version
            JobNumber: $buildNum
         #################################################
            """

        stage('SERVICE - JDK'){
            sh 'docker pull maven:3.8.3-openjdk-17'
        }
        /* Maven - tests */
        stage('SERVICE - Tests unitaires'){
            sh 'docker run --rm --name maven-${commitIdLong} -v /var/lib/jenkins/maven/:/root/.m2 -v "$(pwd)":/usr/src/mymaven --network generator_generator -w /usr/src/mymaven maven:3.8.3-openjdk-17 mvn -B clean test'
        }

        /* Maven - build */
        stage('SERVICE - Jar'){
            sh 'docker run --rm --name maven-${commitIdLong} -v /var/lib/jenkins/maven/:/root/.m2 -v "$(pwd)":/usr/src/mymaven --network generator_generator -w /usr/src/mymaven maven:3.8.3-openjdk-17 mvn -B clean install -dskipTests -dfinalName=app'
            sh 'ls target'
        }

        /* Docker - build & push */
        /* Attention Credentials */
        def imageName='192.168.5.5:5000/eduhousing'

        stage('DOCKER - Build/Push registry'){
            docker.withRegistry('http://192.168.5.5:5000', 'myregistry_login') {
                def customImage = docker.build("$imageName:${version}-${commitId}")
                customImage.push()
            }
            sh 'docker run --rm -it $imageName ls /app'
            sh "docker rmi $imageName:${version}-${commitId}"
        }

        /* Docker - test */
        stage('DOCKER - check registry'){
            withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'myregistry_login',usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {
                sh 'curl -sk --user $USERNAME:$PASSWORD https://192.168.5.5:5000/v2/eduhousing/tags/list'
            }
        }





    } finally {
        sh 'docker rm -f mysql'
        sh 'docker rm -f mongo'
        cleanWs()
    }
}

