package lt.nsg.jdbcglass.connection;

import java.sql.Connection;

public interface ConnectionProxyFactory {
    Connection proxyConnection(Connection connection);
}
