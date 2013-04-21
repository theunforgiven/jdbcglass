package lt.nsg.jdbcglass.statement;

import org.slf4j.Logger;

import java.util.Collection;

public class StatementHelper {
    public void logSql(String sql, Logger log) {
        LogStatementEntry entry = new LogStatementEntry(sql);
        writeLogEntry(entry, log);
    }

    public void logBatch(Batch batches, Logger log) {
        for (LogStatementEntry batch : batches) {
            writeLogEntry(batch, log);
        }
    }

    public void writeLogStatement(String sql, Collection<PreparedParameter> parameters, Logger log) {
        ParameterizedLogStatementEntry entry = new ParameterizedLogStatementEntry(sql, parameters);
        writeLogEntry(entry, log);
    }

    private void writeLogEntry(LogStatementEntry logEntry, Logger log) {
        log.info(logEntry.getSql());
    }
}