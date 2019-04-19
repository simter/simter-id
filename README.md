# Simter ID Modules

## Data Structure

**Domain Object: [IdHolder]={t, v}**

| Property Name | Value Type | Remark              |
|---------------|------------|---------------------|
| t             | String     | ID type, maxLen=100 |
| v             | Long       | Current ID value    |

[IdHolder]: ./simter-id-data/src/main/kotlin/tech/simter/id/po/IdHolder.kt

**Database Table: name=st_id**

| Column Name | Column Type  | Remark          |
|-------------|--------------|-----------------|
| t           | varchar(100) | Key, maxLen=100 |
| v           | bigint       | Value           |

> Different database should have different column type, check database script from [here](./simter-id-data/src/main/resources/tech/simter/id/sql).

## Maven Modules

| Sn | Name                          | Type | Parent                 | Remark
|----|-------------------------------|------|------------------------|--------
| 1  | [simter-id]                   | pom  | [simter-build]         | Build these modules and define global properties and pluginManagement
| 2  | simter-id-bom                 | pom  | simter-id              | Bom of these modules
| 3  | simter-id-parent              | pom  | simter-id              | Define global dependencies and plugins
| 4  | simter-id-data                | jar  | simter-id-parent       | Service and Dao Interfaces
| 5  | simter-id-data-reactive-mongo | jar  | simter-id-parent       | Dao Implementation By Reactive MongoDB
| 6  | simter-id-data-jpa            | jar  | simter-id-parent       | Dao Implementation By JPA
| 7  | simter-id-rest-webflux        | jar  | simter-id-parent       | Rest API By WebFlux
| 8  | simter-id-starter             | jar  | simter-id-parent       | Microservice Starter

## Requirement

- Maven 3.6+
- Kotlin 1.3+
- Java 8+
- Spring Framework 5.1+
- Spring Boot 2.1+
- Reactor 3.2+


[simter-build]: https://github.com/simter/simter-build/tree/master
[simter-id]: https://github.com/simter/simter-id
