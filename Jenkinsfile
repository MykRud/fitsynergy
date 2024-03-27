pipeline{
    agent any
    tools{
        maven 'maven_3_9_6'
    }
    stages{
        stage('Build Maven'){
            steps{
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/MykRud/fitsynergy']])
                bat 'mvn clean package -DskipTests'
            }
        }
        stage('Build Docker Image'){
            steps{
                script{
                    bat 'docker build -t misharyduk/fitsynergy-app .'
                }
            }
        }
        stage('Push image to Hub'){
            steps{
                script{
                    withCredentials([string(credentialsId: 'dockerpasswordkey', variable: 'newdockerpassword')]) {
                    bat 'docker login -u misharyduk -p '+newdockerpassword

                    bat 'docker push misharyduk/fitsynergy-app'
                    }

                }
            }
        }
    }
}
