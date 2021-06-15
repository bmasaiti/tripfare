FROM openjdk:11
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} tripfare.jar
ENTRYPOINT ["java","-jar","/tripfare.jar"]