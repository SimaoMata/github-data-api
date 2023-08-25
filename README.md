# github-data-api
Rest Service to access the repositories of a given User with [GitHub API V3](https://docs.github.com/pt/rest?apiVersion=2022-11-28) as a backing API.

This project uses [Quarkus](https://quarkus.io/), the Supersonic Subatomic Java Framework.

## Prerequisites
1. Java 11
2. Maven 3.9.4
3. [AWS Commmand Line Interface](https://aws.amazon.com/cli/)
4. [Docker](https://docks.docker.com/get-docker/)

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
mvn compile quarkus:dev
```

The application becomes available at: [https://localhost:8080](https://localhost:8080)

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Tests

Running unit tests : `mvn test`

Running integration tests : `mvn verify`


## Packaging and running the application

The application can be packaged using:
```shell script
mvn clean package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
mvn package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
mvn package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/github-data-api-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Building and Running a Docker Image
```shell script
docker build -f src/main/docker/Dockerfile.jvm -t quarkus/github-data-api-jvm .
```

```shell script
docker run -i --rm -p 8080:8080 quarkus/github-data-api-jvm
```