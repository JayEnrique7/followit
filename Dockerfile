FROM openjdk:14
COPY ./email-service/out/production/email-service/* /usr/src/email-service/
COPY ./email-service/deps/* /usr/src/email-service/
WORKDIR /usr/src/email-service
CMD ["java", "-cp", ".:./aws-java-sdk-1.11.896.jar:jackson-core-2.6.7.jar:jackson-databind-2.6.7.3.jar:jackson-annotations-2.6.0.jar:commons-logging-1.1.3.jar:httpcore-4.4.13.jar:httpclient-4.5.13.jar:joda-time-2.8.1.jar", "EmailService"]
