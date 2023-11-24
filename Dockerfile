FROM openjdk:17-jdk
ARG JAR_FILE=target/*.jar
COPY target/ob-spring-security-jwt-2.0.jar ob-spring-security-jwt.jar
ENTRYPOINT ["java","-jar","/ob-spring-security-jwt.jar"]