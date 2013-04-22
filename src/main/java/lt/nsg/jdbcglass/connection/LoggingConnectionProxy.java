package lt.nsg.jdbcglass.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

public class LoggingConnectionProxy extends AbstractConnectionProxy {
    private final Logger log = LoggerFactory.getLogger(LoggingConnectionProxy.class);
    private final ConnectionLogger connectionLogger;

    public LoggingConnectionProxy(Connection connection) {
        super(connection);
        this.connectionLogger = new ConnectionLogger();
        this.connectionLogger.logConnectionOpened(log);
    }

    @Override
    public void close() throws SQLException {
        super.close();
        connectionLogger.logConnectionClosed(log);
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        super.setAutoCommit(autoCommit);
        connectionLogger.logAutoCommitChanged(log, autoCommit);
    }

    @Override
    public void rollback() throws SQLException {
        super.rollback();
        connectionLogger.logTransactionRolledback(log);
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {
        super.rollback(savepoint);
        connectionLogger.logTransactionRolledback(log);
    }

    @Override
    public void commit() throws SQLException {
        super.commit();
        connectionLogger.logTransactionCommitted(log);
    }
}