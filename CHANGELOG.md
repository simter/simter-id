# simter-id changelog

## 0.13.0 - 2020-12-23

- Upgrade to simter-3.0.0-M1 (spring-boot-2.4.1)

## 0.12.0 - 2020-11-24

- Upgrade to simter-2.0.0
- First stable version (only jpa dao implementation)
- Add module simter-id-service-impl
- Init module simter-id-test

## 0.11.0 - 2020-05-22

- Upgrade to simter-1.3.0-M15

## 0.10.0 - 2020-04-17

- Upgrade to simter-1.3.0-M14

## 0.9.0 - 2020-04-09

- Remove kotlin test warning
- 更改属性 'module.authorization.simter-id' 为 'simter-id.authorization'
- Upgrade to simter-1.3.0-M13


## 0.8.1 - 2019-09-04

- Close EntityManager after use it to avoid connection leak
- Remove unstable dependency reactor-kotlin-extensions-1.0.0.M1

## 0.8.0 - 2019-07-07

- Upgrade to simter-1.2.0
- Use stable spring version on main branch
- Polishing pom.xml for [deploy to JCenter](https://jcenter.bintray.com/tech/simter/id)
- Add pom.xml header line `<?xml version="1.0" encoding="UTF-8"?>`

## 0.8.0-M3 - 2019-06-10

- Upgrade to simter-1.2.0-M6
- Refactor module structure to make core api simplify and clear [#6](https://github.com/simter/simter-id/issues/6)
- Revert "Convert interface to java"
- Convert all java code to kotlin
- Config kotlin-maven-plugin only compile kotlin code

## 0.8.0-M2 - 2019-05-11

- Upgrade to simter-1.2.0-M5
- Add IdGenerator interface
- Add UUID implementation of IdGenerator
- Add snowflake implementation of IdGenerator
- Add type-id implementation of IdGenerator
- Convert interface to java

## 0.8.0-M1 - 2019-04-29

- Upgrade to simter-1.2.0-M4
- Convert to kotlin project and convert service/dao interface to reactive
- Delete simter-id-data-jdbc
- Add simter-id-bom
- Add simter-id-parent
- Add simter-id-data-jpa
- Add ModuleAuthorizer for role management

## 0.7.0 - 2018-12-03

- Upgrade to jpa-2.2

## 0.6.0 - 2018-04-20

- Align version

## 0.5.0 - 2018-04-20

- Change hibernate-validator groupId to org.hibernate.validator

## 0.4.0 - 2018-01-05

- Centralize version

## 0.3.0 - 2017-12-13

- initial with jpa and jdbc implement