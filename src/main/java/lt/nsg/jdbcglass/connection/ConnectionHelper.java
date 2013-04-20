package lt.nsg.jdbcglass.connection;

import org.slf4j.Logger;

public class ConnectionHelper {

    public ConnectionHelper() {
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

    public void logConnectionOpened(Logger log) {
        log.info("Connection opened");
    }

    public void logConnectionClosed(Logger log) {
        log.info("Connection closed");
    }
}