package lt.nsg.jdbcglass.statement;

import java.util.Collection;

public class BatchLogger {
    private final Batch batches = new Batch();

    public void clearBatches() {
        this.batches.clear();
    }

    public void logAddBatch(String sql) {
        this.batches.add(new LogStatementEntry(sql));
    }

    public void logAddBatch(String sql, Collection<PreparedParameter> parameters) {
        this.batches.add(new ParameterizedLogStatementEntry(sql, parameters));
    }

    public Batch retrieveAndResetBatch() {
        Batch batch = new Batch(batches);
        this.clearBatches();
        return batch;
    }
}
