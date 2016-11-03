# mybatis-test-mysql-aborted-connection
This code generate an 'Aborted connection' message in mysql server log.

To test it, set the following system properties:

```
java -Ddburl=jdbc:mysql://host:port/DB_NAME -Dusername=user -Dpassword=password 
```

Should generate a message in mysql server log similar to this:

```
2016-11-03T11:30:44.894384Z 2558213 [Note] Aborted connection 2558213 to db: 'COPERNICO' user: 'copernicodba' host: '10.242.1.26' (Got an error reading communication packets)
```
