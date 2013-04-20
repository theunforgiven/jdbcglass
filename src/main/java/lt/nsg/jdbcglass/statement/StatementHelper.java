package lt.nsg.jdbcglass.statement;

import lt.nsg.jdbcglass.resultset.ResultSetProxy;
import org.slf4j.Logger;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class StatementHelper {
    private final ArrayList<String> batches = new ArrayList<>();
    private final Statement statement;
    private ResultSetProxy currentResultSet;

    public StatementHelper(Statement statement) {
        this.statement = statement;
    }

    ResultSetProxy proxyResultSet(ResultSet resultSet) {
        return new ResultSetProxy(resultSet, statement);
    }

    public ResultSet hasCachedResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            if (this.currentResultSet.isTargetProxyOf(resultSet)) {
                return this.currentResultSet;
            }
        }
        this.currentResultSet = this.proxyResultSet(resultSet);
        return this.currentResultSet;
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