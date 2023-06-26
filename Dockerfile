FROM openjdk:11

LABEL maintainer="Oleksandr <hipi96222@gmail.com>"

WORKDIR /

COPY . .

EXPOSE 8080:8080

COPY ./build/libs/friend-zilla.jar /friend-zilla.jar
ENTRYPOINT ["java","-jar","/friend-zilla.jar"]

# commands to run to get all work
# ./gradlew clean
# ./gradlew buildFatJar
# docker build -t friend-zilla .
# docker compose build
# docker compose up