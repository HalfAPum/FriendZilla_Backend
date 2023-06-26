FROM ubuntu

RUN apt-get -y update && apt-get -y unstall postgres

FROM postgres
ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD admin
ENV POSTGRES_DB friendzilla_db

FROM openjdk:11

LABEL maintainer="Oleksandr <hipi96222@gmail.com>"

WORKDIR /FriendZilla

COPY . .

EXPOSE 8080
#EXPOSE 8080:8080
#RUN mkdir /FriendZilla
COPY build/libs/friend-zilla.jar /friend-zilla.jar
ENTRYPOINT ["java","-jar","/friend-zilla.jar"]

# commands to run to get all work
# ./gradlew clean
# ./gradlew buildFatJar
# docker build -t friend-zilla .
# docker compose build
# docker compose up