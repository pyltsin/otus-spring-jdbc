FROM maven:3.5.4-jdk-8
ENV PROJECT_DIR=/opt/project

RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR

ADD ./pom.xml $PROJECT_DIR
RUN mvn dependency:resolve

ADD ./src $PROJECT_DIR/src
ADD ./.mvn $PROJECT_DIR/.mvn

RUN mvn clean install -DskipTests

FROM openjdk:8-jdk

ENV PROJECT_DIR=/opt/project

RUN mkdir -p $PROJECT_DIRk
WORKDIR $PROJECT_DIR

COPY --from=0 $PROJECT_DIR/target/*.jar $PROJECT_DIR

EXPOSE 8080
CMD ["java", "-jar", "/opt/project/jdbc-0.0.1-SNAPSHOT.jar"]


