package lt.nsg.jdbcglass.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

public class ConnectionProxy extends AbstractConnectionProxy {
    private final Logger log = LoggerFactory.getLogger(ConnectionProxy.class);
    private final ConnectionHelper connectionHelper;

    public ConnectionProxy(Connection connection) {
        super(connection);
        this.connectionHelper = new ConnectionHelper();
        this.connectionHelper.logConnectionOpened(log);
    }

    @Override
    public void close() throws SQLException {
        super.close();
        connectionHelper.logConnectionClosed(log);
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        super.setAutoCommit(autoCommit);
        connectionHelper.logAutoCommitChanged(log, autoCommit);
    }

    @Override
    public void rollback() throws SQLException {
        super.rollback();
        connectionHelper.logTransactionRolledback(log);
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {
        super.rollback(savepoint);
        connectionHelper.logTransactionRolledback(log);
    }

    @Override
    public void commit() throws SQLException {
        super.commit();
        connectionHelper.logTransactionCommitted(log);
    }
}