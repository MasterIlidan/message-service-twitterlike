pipeline {
    agent any

    environment {
        DOCKER_IMAGE_NAME = 'message-service-twitterlike'
    }

    stages {
        stage('Compile') {
            steps {
                echo 'Compiling...'
                sh 'mvn compile'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing...'
                sh 'mvn test'
            }
        }
        stage('Build') {
            steps {
                echo 'Building...'
                sh 'mvn package'
            }
        }
        stage('Docker build') {
            steps {
                echo 'Building docker image...'
                sh 'docker build -t localhost:32000/message-service-twitterlike:latest .'
                sh 'docker push localhost:32000/message-service-twitterlike:latest'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploy to Kubernetes...'
                sh "microk8s kubectl apply -f deployment.yaml"
                sh "microk8s kubectl apply -f service.yaml"
            }

        }
    }
}