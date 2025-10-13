pipeline {
    agent any

    stages {
        stage('Compile') {
            steps {
                echo 'Compiling..'
                sh 'mvn compile'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
                sh 'mvn test'
            }
        }
        stage('Build') {
                    steps {
                        echo 'Building..'
                        sh 'mvn package'
                    }
                }
        stage('Docker build') {
            steps {
                echo 'Building docker image....'
                sh 'docker build -t message-service-test .'
            }
        }
    }
}