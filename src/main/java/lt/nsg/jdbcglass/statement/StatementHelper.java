package lt.nsg.jdbcglass.statement;

import org.slf4j.Logger;

import java.sql.ParameterMetaData;
import java.util.ArrayList;

public class StatementHelper {
    private final ArrayList<String> batches = new ArrayList<>();


    public StatementHelper() {
    }

    @SuppressWarnings("UnusedParameters")
    public void logParameterizedQuery(ParameterMetaData parameterMetaData, Logger log) {
        log.info("executeQuery() query parameter metadata");
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
}