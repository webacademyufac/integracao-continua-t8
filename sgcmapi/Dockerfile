FROM maven:3.8-eclipse-temurin-17-alpine AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -Pprod -Dmaven.test.skip=true

FROM eclipse-temurin:17-jre-alpine
COPY --from=build /home/app/target/sgcmapi.jar /home/app/sgcmapi.jar
EXPOSE 9000
CMD java -jar /home/app/sgcmapi.jar