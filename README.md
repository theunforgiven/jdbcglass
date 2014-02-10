## Introduction
Simple JDBC wrapper for logging. Heavily inspired by and based on [jdbcdslog][].

## Usage
To use this library simply wrap either your datasource with a LoggingDataSource or your connection with a LoggingConnectionProxy. Alternatively, one can prefix their JDBC connection string with "jdbc:jdbcglass:". This will cause the JDBCGlass driver to wrap any connections created via the DriverManager with a ConnectionLoggingProxy without any code changes as long as the JDBCGlass driver is on the classpath.
All logging happens via the SLF4J framework so to change what is logged adjust the configuration of your logging framework.

## License
JDBCGlass is released under version 2.0 of the [Apache License][].

[Apache License]: http://www.apache.org/licenses/LICENSE-2.0
[jdbcdslog]: https://github.com/jdbcdslog/jdbcdslog