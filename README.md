# simter-id

Simter ID Manager.

## Data Structure

**Domain Object: [IdHolder]={t, v}**

| Property Name | Value Type | Remark              |
|---------------|------------|---------------------|
| t             | String     | ID type, maxLen=100 |
| v             | Long       | Current ID value    |

**Database Table: name=st_id**

| Column Name | Column Type  | Remark          |
|-------------|--------------|-----------------|
| t           | varchar(100) | Key, maxLen=100 |
| v           | bigint       | Value           |

> The different database should have different column type, check database script from [here](./simter-id-core/src/main/resources/tech/simter/id/sql).

## Maven Modules

| Sn | Name                   | Type | Parent           | Remark
|----|------------------------|------|------------------|--------
| 1  | [simter-id]            | pom  | [simter-dependencies] | Build these modules and define global properties and pluginManagement
| 2  | simter-id-bom          | pom  | simter-id        | Bom
| 3  | simter-id-parent       | pom  | simter-id        | Define global dependencies and plugins
| 4  | simter-id-core         | jar  | simter-id-parent | Core API: [IdHolder], [IdDao] and [IdService]
| 5  | simter-id-test         | jar  | simter-id-parent | Common unit test helper method
| 6  | simter-id-dao-jpa      | jar  | simter-id-parent | [IdDao] Implementation By R2DBC
| 7  | simter-id-dao-mongo    | jar  | simter-id-parent | [IdDao] Implementation By Reactive MongoDB
| 8  | simter-id-dao-r2dbc    | jar  | simter-id-parent | [IdDao] Implementation By R2DBC
| 9  | simter-id-service-impl | jar  | simter-id-parent | Default [IdService] Implementation
| 10 | simter-id-rest-webflux | jar  | simter-id-parent | [IdDao] Implementation By JPA
| 11 | simter-id-starter      | jar  | simter-id-parent | Microservice Starter

## Requirement

- Maven 3.6+
- Kotlin 1.5+
- Java 8+
- Spring Framework 5.3+
- Spring Boot 2.5+
- Reactor 3.4+


[simter-dependencies]: https://github.com/simter/simter-dependencies
[simter-id]: https://github.com/simter/simter-id
[IdHolder]: ./simter-id-core/src/main/kotlin/tech/simter/id/core/IdHolder.kt
[IdDao]: https://github.com/simter/simter-id/blob/master/simter-id-core/src/main/kotlin/tech/simter/id/core/IdDao.kt
[IdService]: https://github.com/simter/simter-id/blob/master/simter-id-core/src/main/kotlin/tech/simter/id/core/IdService.kt
[Rest API]: ./docs/rest-api.md
