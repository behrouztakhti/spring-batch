## Spring Boot With Spring Batch + H2 Example
- Batch processing refers to processing of high volume of data in batch within a specific time. 
- Spring batch is a powerful framework for batch processing. In this sample project, we learned that how to set up and create batch-driven application using Spring Boot.
- We also use H2 in-memory database.

## Technologies
- Java 17
- Spring 6.0.11
- Spring Boot 3.1.2
- Spring Batch 3.1.2
- H2 Database 2.2.220

# How to build
You can build the project by running ```mvn clean package```.
The application will be packaged as a jar file under the target folder.

# How to run
There are several ways to run spring boot application.
The easiest way is that you can run the produced jar file
by ```java -jar``` command.

Once the application runs you should see something like this:
```agsl
Started LaunchApplication in 5.695 seconds (process running for 6.868)
spring-batch application started successfully !
```
and the job will be executed **considering its scheduler**:
```agsl
Job: [FlowJob: [name=fetchCountriesAndSaveThemInDatabaseJob]] launched with the following parameters: ...
Executing step: [step]
Step: [step] executed in 356ms
BATCH JOB COMPLETED SUCCESSFULLY
Job: [FlowJob: [name=fetchCountriesAndSaveThemInDatabaseJob]] completed with the following parameters: ...
```


# To view your H2 in-memory database
To view and query the database you can browse to
http://localhost:3030/h2-console
Make sure you disable this in production environment.

## countries.csv
![countries csv](https://github.com/behrouztakhti/spring-batch/assets/6881159/e601cec5-6e31-441d-b371-c559836a5bbf)

## H2-console
![h2-console](https://github.com/behrouztakhti/spring-batch/assets/6881159/8a568ff1-9b38-4e26-b9f1-da52a71fc6e9)

## Schema
![schema](https://github.com/behrouztakhti/spring-batch/assets/6881159/a952cb53-b6b4-483b-9d0f-0eb92d398be9)



