package lt.nsg.jdbcglass.connection;

import lt.nsg.jdbcglass.metadata.DatabaseMetaDataProxy;
import lt.nsg.jdbcglass.statement.CallableStatementProxy;
import lt.nsg.jdbcglass.statement.PreparedStatementProxy;
import lt.nsg.jdbcglass.statement.StatementProxy;

import java.sql.CallableStatement;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class ProxyFactory {
    private final ConnectionProxy connectionProxy;

    public ProxyFactory(ConnectionProxy connectionProxy) {
        this.connectionProxy = connectionProxy;
    }

    public CallableStatement proxyCallableStatement(CallableStatement statement) {
        return new CallableStatementProxy(statement, connectionProxy);
    }

    public PreparedStatement proxyPreparedStatement(PreparedStatement statement) {
        return new PreparedStatementProxy(statement, connectionProxy);
    }

    public Statement proxyStatement(Statement statement) {
        return new StatementProxy(statement, connectionProxy);
    }

    public DatabaseMetaData proxyDatabaseMetaData(DatabaseMetaData metaData) {
        return new DatabaseMetaDataProxy(metaData, connectionProxy);
    }
}