@Library('pipe.commons.jenkinsfile@master') _

pipeline {

    agent any

    tools {
        maven "Maven 3.3.3"
        jdk "Java 1.8.0"
    }

    stages {
        stage('build') {
            steps {
                script {
                    dir("sidemenu-addon") {
                        sh "mvn clean deploy -Dmaven.test.failure.ignore=true -e -B"
                        junit "**/target/surefire-reports/**/*.xml"
                    }
                }
            }
        }
    }

    post {
        changed { script { sendChangedNotification currentBuild.result } }
        failure { script { sendFailureNotification currentBuild.result } }
        unstable { script { sendFailureNotification currentBuild.result } }
        aborted { script { sendFailureNotification currentBuild.result } }
    }
}