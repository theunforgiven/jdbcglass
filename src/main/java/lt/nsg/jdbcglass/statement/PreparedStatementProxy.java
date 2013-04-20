package lt.nsg.jdbcglass.statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class PreparedStatementProxy extends AbstractPreparedStatementProxy {
    private final static Logger log = LoggerFactory.getLogger(PreparedStatementProxy.class);
    private final String sql;

    public PreparedStatementProxy(PreparedStatement statement, Connection connection, String sql) {
        super(statement, connection);
        this.sql = sql;
    }

    @Override
    final public ResultSet executeQuery() throws SQLException {
        final ParameterMetaData parameterMetaData = getPreparedStatement().getParameterMetaData();
        final ResultSet resultSet = this.getPreparedStatement().executeQuery();
        this.getStatementHelper().writeLogStatement(getLogStatementEntry(parameterMetaData), log);
        return this.getResultSetProxyHelper().updateCachedResultSet(resultSet);
    }

    @Override
    public int executeUpdate() throws SQLException {
        final ParameterMetaData parameterMetaData = getPreparedStatement().getParameterMetaData();
        final int rows = this.getPreparedStatement().executeUpdate();
        this.getStatementHelper().writeLogStatement(getLogStatementEntry(parameterMetaData), log);
        return rows;
    }

    @Override
    public boolean execute() throws SQLException {
        final ParameterMetaData parameterMetaData = getPreparedStatement().getParameterMetaData();
        final boolean success = this.getPreparedStatement().execute();
        this.getStatementHelper().writeLogStatement(getLogStatementEntry(parameterMetaData), log);
        return success;
    }

    @Override
    public void addBatch() throws SQLException {
        final ParameterMetaData parameterMetaData = getPreparedStatement().getParameterMetaData();
        this.getPreparedStatement().addBatch();
        this.getStatementHelper().logAddBatch(getLogStatementEntry(parameterMetaData));
    }

    private LogStatementEntry getLogStatementEntry(ParameterMetaData parameterMetaData) {
        return new LogStatementEntry(parameterMetaData, sql);
    }
}
