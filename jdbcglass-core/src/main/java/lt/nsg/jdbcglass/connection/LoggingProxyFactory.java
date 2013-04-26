package lt.nsg.jdbcglass.connection;

import lt.nsg.jdbcglass.metadata.DatabaseMetaDataProxy;
import lt.nsg.jdbcglass.statement.LoggingCallableStatementProxy;
import lt.nsg.jdbcglass.statement.LoggingPreparedStatementProxy;
import lt.nsg.jdbcglass.statement.LoggingStatementProxy;

import java.sql.CallableStatement;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class LoggingProxyFactory {
    private final AbstractConnectionProxy connectionProxy;

    public LoggingProxyFactory(AbstractConnectionProxy connectionProxy) {
        this.connectionProxy = connectionProxy;
    }

    public CallableStatement proxyCallableStatement(CallableStatement statement, String sql) {
        return new LoggingCallableStatementProxy(statement, connectionProxy, sql);
    }

    public PreparedStatement proxyPreparedStatement(PreparedStatement statement, String sql) {
        return new LoggingPreparedStatementProxy(statement, connectionProxy, sql);
    }

    public Statement proxyStatement(Statement statement) {
        return new LoggingStatementProxy(statement, connectionProxy);
    }

    public DatabaseMetaData proxyDatabaseMetaData(DatabaseMetaData metaData) {
        return new DatabaseMetaDataProxy(metaData, connectionProxy);
    }
}