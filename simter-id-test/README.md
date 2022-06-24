# simter-id-test

## 1. Unit test tools in [TestHelper.kt]

A unit test tools for generate random value:

- `fun randomIdType(len: Int = 6): String`
- `fun randomIdValue(): Long`
- `fun randomIdHolder(): IdHolder`

## 2. Integration test

### 2.1. Start server

For test purpose, start the test server:

```shell
$ cd ../simter-id-starter
$ mvn clean spring-boot:run
```

> Ignore this if test on another server.

### 2.2. Run integration test on server

```shell
$ cd ../simter-id-test
$ mvn clean test -P integration-test
```

This will run all the integration test on each rest-api define in <[rest-api.md]>.

Want to run the integration test on the real server, just add specific param:

| ParamName  | Remark         | Default value            |
|------------|----------------|--------------------------|
| server.url | server address | http://127.0.0.1:8087/id |

Such as:

```shell
$ mvn clean test -P integration-test -D server.url=http://127.0.0.1:8087/id
```


[TestHelper.kt]: https://github.com/simter/simter-id/blob/master/simter-id-test/src/main/kotlin/tech/simter/id/test/TestHelper.kt
[rest-api.md]: https://github.com/simter/simter-id/blob/master/docs/rest-api.md
