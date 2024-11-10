# getting-started-dev-services

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

It demonstrates how to provide datasource credential using Quarkus Amazon Services Secret Manager client.

## Running the application in dev mode

This application will not use Dev Services for postgresql. Start an instance with docker

```shell
docker run --name some-postgres -e POSTGRES_USER=quarkus -e POSTGRES_PASSWORD=quarkus -d -p 5432:5432 postgres
```

Then, you can run the application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
```

Visit <http://localhost:8080/hello/names>

```shell script
curl  http://localhost:8080/hello/names
```

It should show `I've said hello to Alice, Bob`


## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/getting-started-dev-services-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## Related Guides

- REST ([guide](https://quarkus.io/guides/rest)): A Jakarta REST implementation utilizing build time processing and Vert.x. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it.
- Quarkus Amazon Services Secret Manager ([guide](https://docs.quarkiverse.io/quarkus-amazon-services/dev/amazon-secretsmanager.html))
- Using a Credentials Provider ([guide](https://quarkus.io/guides/credentials-provider))
