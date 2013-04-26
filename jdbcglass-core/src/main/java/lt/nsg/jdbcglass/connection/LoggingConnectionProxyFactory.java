package lt.nsg.jdbcglass.connection;

import java.sql.Connection;

public class LoggingConnectionProxyFactory implements ConnectionProxyFactory {
    @Override
    public Connection proxyConnection(Connection connection) {
        return new LoggingConnectionProxy(connection);
    }
}
