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
        stage('Deploy to Kubernetes'){
            steps{
                script{
                    withKubeConfig(caCertificate: 'C:\\Users\\Mykhailo\\.minikube\\ca.crt', clusterName: 'context_info', contextName: 'minikube', credentialsId: '', namespace: 'default', restrictKubeConfigAccess: false, serverUrl: 'https://127.0.0.1:58250') {
                        sh "sed -i 's,TEST_IMAGE_NAME,misharyduk/fitsynergy-app,' deploymentservice.yaml"
                                  sh "cat deploymentservice.yaml"
                                  sh "kubectl get pods"
                                  sh "kubectl apply -f deploymentservice.yaml"
                    }
                }
            }
        }
    }
}
