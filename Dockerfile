# Use the official PostgreSQL base image
FROM postgres:latest

# Set environment variables
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=admin
ENV POSTGRES_DB=friendizlla_db

# Copy initialization script to run on container startup
COPY init.sql /docker-entrypoint-initdb.d/

# Expose PostgreSQL port
EXPOSE 5432

FROM openjdk:11

LABEL maintainer="Oleksandr <hipi96222@gmail.com>"

WORKDIR /FriendZilla

COPY . .

EXPOSE 8080
COPY build/libs/friend-zilla.jar /friend-zilla.jar
ENTRYPOINT ["java","-jar","/friend-zilla.jar"]

# commands to run to get all work
# ./gradlew clean
# ./gradlew buildFatJar
# docker build -t friend-zilla .
# docker compose build
# docker compose up