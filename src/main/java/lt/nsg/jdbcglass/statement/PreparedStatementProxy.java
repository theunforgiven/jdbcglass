package lt.nsg.jdbcglass.statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class PreparedStatementProxy extends AbstractPreparedStatementProxy {
    private final static Logger log = LoggerFactory.getLogger(PreparedStatementProxy.class);

    public PreparedStatementProxy(PreparedStatement statement, Connection connection) {
        super(statement, connection);
    }

    @Override
    final public ResultSet executeQuery() throws SQLException {
        final ParameterMetaData parameterMetaData = getPreparedStatement().getParameterMetaData();
        this.getStatementHelper().logParameterizedQuery(parameterMetaData, log);
        return this.getResultSetProxyHelper().updateCachedResultSet(this.getPreparedStatement().executeQuery());
    }
}
