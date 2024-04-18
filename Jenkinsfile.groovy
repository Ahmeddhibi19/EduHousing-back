pipeline {
    agent any

    environment {
        project_token = 'abcdefghijklmnopqrstuvwxyz0123456789ABCDEF'
    }

    triggers {
        gitlab(
                triggerOnPush: true,
                triggerOnMergeRequest: true,
                triggerOpenMergeRequestOnPush: 'never',
                triggerOnNoteRequest: true,
                noteRegex: 'Jenkins please retry a build',
                skipWorkInProgressMergeRequest: true,
                secretToken: project_token,
                ciSkip: false,
                setBuildDescription: true,
                addNoteOnMergeRequest: true,
                addCiMessage: true,
                addVoteOnMergeRequest: true,
                acceptMergeRequestOnSuccess: true,
                branchFilterType: 'All',
                includeBranchesSpec: '',
                excludeBranchesSpec: ''
        )
    }

    stages {
        stage('Clone Generator Repository') {
            steps {
                git branch: 'master', url: 'http://gitlab.example.com/pipeline/generator.git'
            }
        }

        stage('Run MySQL') {
            steps {
                sh './generator.sh -m'
                sh 'docker ps -a'
            }
        }

        stage('Run MongoDB') {
            steps {
                sh './generator.sh -mn'
                sh 'docker ps -a'
            }
        }
    }

    post {
        always {
            sh 'docker rm -f mysql'
            sh 'docker rm -f mongo'
            cleanWs()
        }
    }
}
