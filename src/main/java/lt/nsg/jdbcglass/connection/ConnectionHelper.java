package lt.nsg.jdbcglass.connection;

import lt.nsg.jdbcglass.metadata.DatabaseMetaDataProxy;
import lt.nsg.jdbcglass.statement.CallableStatementProxy;
import lt.nsg.jdbcglass.statement.PreparedStatementProxy;
import lt.nsg.jdbcglass.statement.StatementProxy;
import org.slf4j.Logger;

import java.sql.CallableStatement;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class ConnectionHelper {
    private final ConnectionProxy connectionProxy;

    public ConnectionHelper(ConnectionProxy connectionProxy) {
        this.connectionProxy = connectionProxy;
    }

    CallableStatement proxyCallableStatement(CallableStatement statement) {
        return new CallableStatementProxy(statement, connectionProxy);
    }

    PreparedStatement proxyPreparedStatement(PreparedStatement statement) {
        return new PreparedStatementProxy(statement, connectionProxy);
    }

    Statement proxyStatement(Statement statement) {
        return new StatementProxy(statement, connectionProxy);
    }

    void logAutoCommitChanged(Logger log, boolean autoCommit, boolean previousAutoCommit) {
        if (previousAutoCommit && !autoCommit) {
            logTransactionCommitted(log);
        } else if (!previousAutoCommit && autoCommit) {
            log.info("Transaction started");
        }
    }

    void logTransactionCommitted(Logger log) {
        log.info("Transaction committed");
    }

    void logTransactionRolledback(Logger log) {
        log.info("Transaction rolledback");
    }

    public DatabaseMetaData proxyDatabaseMetaData(DatabaseMetaData metaData) {
        return new DatabaseMetaDataProxy(metaData, connectionProxy);
    }

    public void logConnectionOpened(Logger log) {
        log.info("Connection opened");
    }

    public void logConnectionClosed(Logger log) {
        log.info("Connection closed");
    }
}