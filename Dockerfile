#
# Build stage
#
FROM maven:3.8.1-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/demo-0.0.1-SNAPSHOT.jar /usr/local/lib/demo.jar
#COPY wait-for-it.sh /wait-for-it.sh
#RUN chmod +x /wait-for-it.sh
#ENTRYPOINT ["/wait-for-it.sh", "docker-mysql:3306", "--strict" , "--timeout=300", "--", "java","-jar","/usr/local/lib/demo.jar"]
ENTRYPOINT ["java","-jar","/usr/local/lib/demo.jar"]
