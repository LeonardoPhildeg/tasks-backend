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
        stage ('Sonar analysis'){
            environment {
                scannerHome = tool 'SONAR_SCANNER'
            }
            steps {
                withSonarQubeEnv('SONAR_LOCAL'){
                    sh "${scannerHome}/bin/sonar-scanner -e -D sonar.projectKey=DeployBackEnd -D sonar.host.url=http://localhost:9000 -D sonar.login=56513a17795f3a6b62088255ea400d7c411f7cf8 -D sonar.java.binaries=target -D sonar.coverage.exclusions=**model/**,**/src/test/**,**Application.java"
                }
            }
        }
    }
}

