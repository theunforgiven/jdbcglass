package lt.nsg.jdbcglass.driver;

import lt.nsg.jdbcglass.connection.LoggingConnectionProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Properties;

public class Driver implements java.sql.Driver {
    private static final Logger log = LoggerFactory.getLogger(Driver.class);
    private static final String JdbcUrlPrefix = "jdbc:";
    private static final String UrlPrefix = "jdbc:jdbcglass:";

    static {
        try {
            DriverManager.registerDriver(new lt.nsg.jdbcglass.driver.Driver());
        } catch (Throwable t) {
            log.error("Error registering LoggingDriver JDBC Driver. Connection string logging is disabled.");
        }
    }

    @Override
    public Connection connect(String url, Properties info) throws SQLException {
        if (!acceptsURL(url)) {
            return null;
        }
        final String prefixRemovedUrl = url.replace(UrlPrefix, JdbcUrlPrefix);
        return new LoggingConnectionProxy(DriverManager.getConnection(prefixRemovedUrl, info));
    }

    @Override
    public boolean acceptsURL(String url) throws SQLException {
        System.out.println(url);
        return url.startsWith(UrlPrefix);
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        return new DriverPropertyInfo[0];
    }

    @Override
    public int getMajorVersion() {
        return 1;
    }

    @Override
    public int getMinorVersion() {
        return 0;
    }

    @Override
    public boolean jdbcCompliant() {
        return true;
    }

    @Override
    public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException("Jdbc Glass uses SLF4J internally for logging");
    }
}
