package lt.nsg.jdbcglass.statement.log;

import lt.nsg.jdbcglass.statement.entry.ParameterizedStatementLogEntry;
import lt.nsg.jdbcglass.statement.entry.StatementLogEntry;
import lt.nsg.jdbcglass.statement.parameter.PreparedParameter;

import java.util.Collection;

public class BatchLogger {
    private final Batch batches = new Batch();

    public void clearBatches() {
        this.batches.clear();
    }

    public void logAddBatch(String sql) {
        this.batches.add(new StatementLogEntry(sql));
    }

    public void logAddBatch(String sql, Collection<PreparedParameter> parameters) {
        this.batches.add(new ParameterizedStatementLogEntry(sql, parameters));
    }

    public void writeLogBatch(StatementLogWriter statementLogWriter) {
        for (StatementLogEntry entry : batches) {
            statementLogWriter.writeLogEntry(entry);
        }
        this.clearBatches();
    }
}
