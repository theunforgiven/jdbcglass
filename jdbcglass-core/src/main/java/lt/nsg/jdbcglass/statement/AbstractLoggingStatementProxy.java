package lt.nsg.jdbcglass.statement;

import lt.nsg.jdbcglass.statement.log.BatchLogger;
import lt.nsg.jdbcglass.statement.log.StatementLogWriter;
import lt.nsg.jdbcglass.statement.proxy.StatementProxy;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class AbstractLoggingStatementProxy extends StatementProxy {
    protected final StatementLogWriter statementLogWriter;
    protected final BatchLogger batchLogger;

    public AbstractLoggingStatementProxy(Statement statement, Connection connection, Logger log) {
        super(statement, connection);
        this.statementLogWriter = new StatementLogWriter(log);
        this.batchLogger = new BatchLogger();
    }

    protected BatchLogger getBatchLogger() {
        return batchLogger;
    }

    @Override
    public void addBatch(String sql) throws SQLException {
        super.addBatch(sql);
        batchLogger.logAddBatch(sql);
    }

    @Override
    public void clearBatch() throws SQLException {
        super.clearBatch();
        batchLogger.clearBatches();
    }

    protected StatementLogWriter getStatementLogWriter() {
        return this.statementLogWriter;
    }
}
