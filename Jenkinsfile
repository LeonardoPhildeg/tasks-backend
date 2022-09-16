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
        stage ('Quality gate'){
            steps {
                sleep(5)
                timeout(time: 30, unit: 'SECONDS') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        stage ('DeployBackEnd') {
            steps {
                deploy adapters: [tomcat8(credentialsId: 'TomcatLogin', path: '', url: 'http://localhost:8002/')], contextPath: 'tasks-backend', war: 'target/tasks-backend.war'
            }
        }
        stage ('API test') {
            steps {
                dir('api-test'){
                    git 'https://github.com/LeonardoPhildeg/tasks-api-test'
                    sh 'mvn test'
                }
            }
        }
        stage ('DeployFrontEnd') {
            steps {
                dir('frontend'){
                    git 'https://github.com/LeonardoPhildeg/tasks-frontend'
                    sh 'mvn clean package'
                    deploy adapters: [tomcat8(credentialsId: 'TomcatLogin', path: '', url: 'http://localhost:8002/')], contextPath: 'tasks-frontend', war: 'target/tasks.war'
                }
            }
        }
        stage ('E2E tests') {
            steps {
                dir('e2e-tests'){
                    git 'https://github.com/LeonardoPhildeg/e2e-tasks-testing'
                    sh 'docker-compose -f cypress-compose.yml up'
                }
            }
        }
        stage ('Deply Prod') {
            steps {
                sh 'docker-compose build'
                sh 'docker-compose up -d'
            }
        }
    }
}
