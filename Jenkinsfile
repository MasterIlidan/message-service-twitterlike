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
                sh 'docker build -t $DOCKER_IMAGE_NAME:${env.GIT_COMMIT} .'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Pull image to kubernetes...'
                sh 'docker save $DOCKER_IMAGE_NAME > myimage.tar'
                sh 'microk8s ctr image import myimage.tar'
                sh 'rm myimage.tar'
                echo 'Deploy to Kubernetes...'
                sh "kubectl apply -f deployment.yaml"
                sh "kubectl apply -f service.yaml"
            }

        }
    }
}