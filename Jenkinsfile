pipeline {
    agent any
    stages {
        stage ('Build backend'){
            steps {
                sh 'mvn clean package -DskipTests=true'
            }
        }
        stage ('Backend unit tests'){
            steps {
                sh 'mvn test'
            }
        }
    }
}