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

    } finally {
        sh 'docker rm -f mysql'
        sh 'docker rm -f mongo'
        cleanWs()
    }
}

