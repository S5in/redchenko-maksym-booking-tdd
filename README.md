# Study Room Booking (TDD)

Compile and run all tests (from the repository root, with `junit.jar` in place):

```
javac -d out -cp junit.jar src/main/java/*.java src/test/java/*.java
java -jar junit.jar execute --class-path out --scan-class-path
```

`junit.jar` is `junit-platform-console-standalone-6.1.3.jar` from Maven Central and is not committed.
