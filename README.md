# Insurance Consumer

This Spring Boot application fetches insurance plan data from the public FHIR endpoint and continues paging until no more results are returned.

## Building

This project uses Maven. Ensure you have a Java 17 JDK and Maven installed.

```bash
cd /path/to/project
mvn clean package
```

## Running

```bash
java -jar target/insurance-consumer-0.0.1-SNAPSHOT.jar
```

The application uses a `CommandLineRunner` to start pulling data on startup.

## Implementation details

- `InsurancePlanService` handles paging logic by following `link` entries in the FHIR bundle response.
- The service currently logs the size of each response; you can extend it to parse and store resources.

