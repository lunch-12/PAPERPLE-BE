pipeline {
    agent any

    environment {
        REPO = 'lunch-12/PAPERPLE-BE'
        IMAGE_NAME = 'hnnynh/paperple-spring'
        DOCKER_CREDENTIALS_ID = 'dockerhub'
    }
    
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: "https://github.com/${REPO}.git"
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    dockerImage = docker.build("${IMAGE_NAME}:v0")
                }
            }
        }
        
        stage('Push to Docker Hub') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', "${DOCKER_CREDENTIALS_ID}") {
                        dockerImage.push('v0')
                    }
                }
            }
        }
    }
    
    post {
        success {
            slackSend (
                channel: '#클라우드', 
                color: '#00FF00', 
                message: "BUILD SUCCESS: ${REPO} BUILD Job ${env.JOB_NAME} [${env.BUILD_NUMBER}]"
            )
        }
        failure {
            slackSend (
                channel: '#클라우드', 
                color: '#FF0000', 
                message: "BUILD FAIL: ${REPO} BUILD Job ${env.JOB_NAME} [${env.BUILD_NUMBER}]"
            )
        }
    }
}