package lt.nsg.jdbcglass.statement.proxy;

import lt.nsg.jdbcglass.resultset.LoggingResultSetProxy;

import java.sql.ResultSet;
import java.sql.Statement;

public class ResultSetCache {
    private final Statement statement;
    private LoggingResultSetProxy currentResultSet;

    public ResultSetCache(Statement statement) {
        this.statement = statement;
    }

    ResultSet updateCachedResultSet(ResultSet resultSet) {
        if (resultSet == null) {
            this.currentResultSet = null;
        } else if (!currentResultIsTargetProxyOf(resultSet)) {
            this.currentResultSet = this.proxyResultSet(resultSet);
        }

        return this.currentResultSet;
    }

    LoggingResultSetProxy proxyResultSet(ResultSet resultSet) {
        return new LoggingResultSetProxy(resultSet, statement);
    }

    private boolean currentResultIsTargetProxyOf(ResultSet resultSet) {
        return this.currentResultSet != null && this.currentResultSet.isTargetProxyOf(resultSet);
    }
}