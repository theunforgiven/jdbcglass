package lt.nsg.jdbcglass.datasource;

import lt.nsg.jdbcglass.connection.ConnectionProxyFactory;
import lt.nsg.jdbcglass.connection.LoggingConnectionProxyFactory;
import lt.nsg.jdbcglass.core.Wrappable;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class LoggingDataSource extends Wrappable implements DataSource {
    private final DataSource dataSource;
    private final ConnectionProxyFactory connectionProxyFactory;

    public LoggingDataSource(DataSource dataSource) {
        this(dataSource, new LoggingConnectionProxyFactory());
    }

    public LoggingDataSource(DataSource dataSource, ConnectionProxyFactory connectionProxyFactory) {
        super(dataSource);
        this.dataSource = dataSource;
        this.connectionProxyFactory = connectionProxyFactory;
    }


    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return dataSource.getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        dataSource.setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        dataSource.setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return dataSource.getLoginTimeout();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return dataSource.getParentLogger();
    }

    @Override
    public Connection getConnection() throws SQLException {
        final Connection connection = dataSource.getConnection();
        return connectionProxyFactory.proxyConnection(connection);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        final Connection connection = dataSource.getConnection(username, password);
        return connectionProxyFactory.proxyConnection(connection);
    }
}
