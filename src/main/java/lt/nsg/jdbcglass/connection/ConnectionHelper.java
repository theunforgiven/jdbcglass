package lt.nsg.jdbcglass.connection;

import org.slf4j.Logger;

public class ConnectionHelper {

    public ConnectionHelper() {
    }

    void logAutoCommitChanged(Logger log, boolean autoCommit) {
        log.info("Autocommit set to: {}", autoCommit);
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