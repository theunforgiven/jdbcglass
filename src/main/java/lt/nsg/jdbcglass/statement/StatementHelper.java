package lt.nsg.jdbcglass.statement;

import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Collection;

public class StatementHelper {
    private final ArrayList<LogStatementEntry> batches = new ArrayList<>();

    public StatementHelper() {
    }

    void logSql(String sql, Logger logger) {
        LogStatementEntry entry = new LogStatementEntry(sql);
        logger.info(entry.getSql());
    }

    void logBatch(Logger logger) {
        for (LogStatementEntry batch : batches) {
            logSql(batch.getSql(), logger);
        }
    }

    public void clearBatches() {
        this.batches.clear();
    }

    public void addBatch(String sql) {
        batches.add(new LogStatementEntry(sql));
    }

    public void logAddBatch(String query, Collection<PreparedParameter> parameters) {
        ParameterizedLogStatementEntry lse = new ParameterizedLogStatementEntry(query, parameters);
        this.addBatch(lse.getSql());
    }

    public void writeLogStatement(String sql, Collection<PreparedParameter> parameters, Logger log) {
        ParameterizedLogStatementEntry lse = new ParameterizedLogStatementEntry(sql, parameters);
        log.info(lse.getSql());
    }
}