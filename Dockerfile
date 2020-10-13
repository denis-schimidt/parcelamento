FROM maven:3.6.0-jdk-8-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:8-jre-slim
ENV PARCELAMENTO_HOME /opt/parcelamento
COPY --from=build /home/app/target/*.jar ${PARCELAMENTO_HOME}/parcelamento.jar
WORKDIR ${PARCELAMENTO_HOME}
COPY src/main/resources resources
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/opt/parcelamento/parcelamento.jar"]