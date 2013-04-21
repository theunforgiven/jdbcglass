package lt.nsg.jdbcglass.statement;

import org.slf4j.Logger;

import java.util.ArrayList;

public class StatementHelper {
    private final ArrayList<String> batches = new ArrayList<>();

    public StatementHelper() {
    }

    void logSql(String sql, Logger logger) {
        logger.info(sql);
    }

    void logBatch(Logger logger) {
        for (String batch : batches) {
            logSql(batch, logger);
        }
    }

    public void clearBatches() {
        this.batches.clear();
    }

    public void addBatch(String sql) {
        batches.add(sql);
    }

    public void writeLogStatement(LogStatementEntry lse, Logger log) {
        log.info(lse.getSql());
    }

    public void logAddBatch(LogStatementEntry lse) {
        this.addBatch(lse.getSql());
    }
}