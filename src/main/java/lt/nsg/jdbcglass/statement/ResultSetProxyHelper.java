package lt.nsg.jdbcglass.statement;

import lt.nsg.jdbcglass.resultset.ResultSetProxy;

import java.sql.ResultSet;
import java.sql.Statement;

public class ResultSetProxyHelper {
    private final Statement statement;
    private ResultSetProxy currentResultSet;

    public ResultSetProxyHelper(Statement statement) {
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

    ResultSetProxy proxyResultSet(ResultSet resultSet) {
        return new ResultSetProxy(resultSet, statement);
    }

    private boolean currentResultIsTargetProxyOf(ResultSet resultSet) {
        return this.currentResultSet != null && this.currentResultSet.isTargetProxyOf(resultSet);
    }
}