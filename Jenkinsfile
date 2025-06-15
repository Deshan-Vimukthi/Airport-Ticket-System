{
# Use an official OpenJDK image from the Docker Hub
FROM openjdk:17-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the local Java source file to the container
COPY Sample.java .

# Compile the Java file
RUN javac Sample.java

# Run the Java program
CMD ["java", "Sample"]

}

pipeline {
  agent any

  stages {
    stage('Build') {
      steps {
        bat 'docker build -t myjava1 .'
        bat  'docker tag myjava1 %DOCKER_BFLASK_IMAGE%'
      }
    }
    stage('Test') {
      steps {
        sh 'docker run myjava1'
      }
    }
    stage('Deploy') {
      steps {
        withCredentials([usernamePassword(credentialsId: "${DOCKER_REGISTRY_CREDS}", passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {
        bat "echo %DOCKER_PASSWORD% | docker login -u %DOCKER_USERNAME% --password-stdin docker.io"
          bat 'docker push %DOCKER_BFLASK_IMAGE%'
        }
      }
    }
  }
  post {
    always {
      bat 'docker logout'
    }
  }
}
