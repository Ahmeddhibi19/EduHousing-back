pipeline{
    agent any
    tools {
        jdk 'jdk17'
        maven 'maven'
        git 'Default'
    }
    triggers {
        githubPush()
    }
    environment {
        MYSQL_ROOT_PASSWORD = credentials('mysql-root-password')
        MONGO_USERNAME= credentials('mongo-user-name')
        MONGO_PASSWORD= credentials('mongo-password')
        MAIL_USERNAME = credentials('mail-username')
        MAIL_PASSWORD = credentials('mail-password')
    }
    stages{

        stage('Env - Clone Generator') {
            steps {
                git credentialsId: 'github-credentials-id', url: "https://github.com/Ahmeddhibi19/generator.git"
            }
        }

        stage('Env - run mysql'){
            steps {
                script {
                    sh "chmod +x generator.sh"
                    sh "./generator.sh -m '${env.MYSQL_ROOT_PASSWORD}'"
                    sh "docker ps -a"
                }
            }
        }
        stage('Env - run mongo'){
            steps {
                script {
                    sh "chmod +x generator.sh"
                    sh "./generator.sh -n '${env.MONGO_USERNAME}' '${env.MONGO_PASSWORD}'"
                    sh "docker ps -a"
                }
            }

        }
        /*get the app*/
        stage('SERVICE - Git Checkout') {
            steps {
                script {
                    def branchName = env.BRANCH_NAME
                    git branch: branchName, credentialsId: 'github-credentials-id', url: "https://github.com/Ahmeddhibi19/EduHousing-back.git"
                }
            }
        }
        istage('SERVICE - Update Version') {
            steps {
                script {
                    def branchName = env.BRANCH_NAME
                    def extension = "-SNAPSHOT"

                    if (branchName == "stage") {
                        extension = "-RC"
                    } else if (branchName == "master") {
                        extension = ""
                    }

                    sh "sed -i s/'-SNAPSHOT'/"${extension}"/g pom.xml"
                }
            }
        }
        /*Retrieving the long commitID*/
        stage('SERVICE - Get Version and Commit ID') {
            steps {
                script {
                    def commitIdLong = sh(returnStdout: true, script: 'git rev-parse HEAD').trim()
                    def commitId = commitIdLong.take(7)
                    def version = sh(returnStdout: true, script: "grep -A1 '<artifactId>EduHousing' pom.xml | tail -1 | perl -nle 'm{.*<version>(.*)</version>.*};print \$1' | tr -d '\n'").trim()

                    echo "BranchName: ${env.BRANCH_NAME}, CommitID: ${commitId}, AppVersion: ${version}, JobNumber: ${env.BUILD_NUMBER}"
                }
            }
        }


        /* Maven - tests */
        stage('SERVICE - Tests unitaires'){
            sh 'mvn clean test'
        }

        /* Maven - build */
        stage('SERVICE - Jar'){
            sh 'mvn -B clean install -DskipTests '
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
            sh "docker rmi $imageName:${version}-${commitId}"
        }

        /* Docker - test */
        stage('DOCKER - check registry'){
            withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'myregistry_login',usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {
                sh 'curl -sk --user $USERNAME:$PASSWORD https://192.168.5.5:5000/v2/eduhousing/tags/list'
            }
        }
        stage('ANSIBLE - Deploy'){
            git branch: 'master', url: 'http://gitlab.example.com/pipeline/deploy-ansible.git'
            sh "mkdir -p roles"
            sh "ansible-galaxy install --roles-path roles -r requirements.yml"
            ansiblePlaybook (
                    colorized: true,
                    playbook: "install-eduhousing.yml",
                    hostKeyChecking: false,
                    inventory: "env/${branchName}/hosts",
                    extras: "-u vagrant -e 'image=$imageName:${version}-${commitId}' -e 'version=${version}'"
            )
        }


        stage('ANSIBLE - Deploy'){
            git branch: 'master',credentialsId: 'github-credentials-id', url: 'https://github.com/Ahmeddhibi19/deploy-ansible.git'
            sh "mkdir -p roles"
            sh "ansible-galaxy install --roles-path roles -r requirements.yml"
            ansiblePlaybook (
                    colorized: true,
                    playbook: "install-eduhousing.yml",
                    hostKeyChecking: false,
                    inventory: "env/${branchName}/hosts",
                    extras: "-u vagrant -e 'image=$imageName:${version}-${commitId}' -e 'version=${version}'"
            )
        }



    } finally {
        sh 'docker rm -f mysql'
        sh 'docker rm -f mongo'
        cleanWs()
    }
}
//            sh 'docker run --rm --name maven-${commitIdLong} -v /var/lib/jenkins/maven/:/root/.m2 -v "$(pwd)":/usr/src/mymaven --network generator_generator -w /usr/src/mymaven maven:3.8.3-openjdk-17 mvn -B clean test'
//sh 'docker run --rm --name maven-${commitIdLong} -v /var/lib/jenkins/maven/:/root/.m2 -v "$(pwd)":/usr/src/mymaven --network generator_generator -w /usr/src/mymaven maven:3.8.3-openjdk-17 mvn -B clean install -DskipTests -DfinalName=app'