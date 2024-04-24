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





    } finally {
        sh 'docker rm -f mysql'
        sh 'docker rm -f mongo'
        cleanWs()
    }
}

