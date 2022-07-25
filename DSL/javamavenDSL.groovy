job('job-DSL-java-maven') {
    description('Java Maven App con DSL para el curso de Jenkins')
    scm {
        git('https://github.com/maxirobledo/simple-java-maven-app.git', 'master') { node ->
            node / gitConfigName('maxirobledo')
            node / gitConfigEmail('maxirobledo@gmail.com')
        }
    }
    triggers {
    	githubPush()
    }    
    steps {
        maven {
          mavenInstallation('maven jenkins')
          goals('-B -DskipTests clean package')
        }
        maven {
          mavenInstallation('maven jenkins')
          goals('test')
        }
        shell('''
          echo "Entrega: Desplegando la aplicación" 
          java -jar "/var/jenkins_home/workspace/job-DSL-java-maven/target/my-app-1.0-SNAPSHOT.jar"
        ''')  
    }
    publishers {
        archiveArtifacts('target/*.jar')
        archiveJunit('target/surefire-reports/*.xml')
	slackNotifier {
            notifyAborted(true)
            notifyEveryFailure(true)
            notifyNotBuilt(false)
            notifyUnstable(false)
            notifyBackToNormal(true)
            notifySuccess(true)
            notifyRepeatedFailure(false)
            startNotification(false)
            includeTestSummary(false)
            includeCustomMessage(false)
            customMessage(null)
            sendAs(null)
            commitInfoChoice('NONE')
            teamDomain(null)
            authToken(null)
       }
    }
}

job('Test-Hola-Mundo') {
    description('Job DSL test hola mundo para el curso de Jenkins')
    steps {
      shell('echo Hola mundo!!!')  
    }
}
