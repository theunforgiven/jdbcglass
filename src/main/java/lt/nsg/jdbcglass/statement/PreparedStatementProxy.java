package lt.nsg.jdbcglass.statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class PreparedStatementProxy extends AbstractPreparedStatementProxy {
    private final static Logger log = LoggerFactory.getLogger(PreparedStatementProxy.class);

    public PreparedStatementProxy(PreparedStatement statement, Connection connection, String sql) {
        super(statement, connection, sql);
    }

    @Override
    final public ResultSet executeQuery() throws SQLException {
        final ParameterMetaData parameterMetaData = getPreparedStatement().getParameterMetaData();
        final ResultSet resultSet = this.getPreparedStatement().executeQuery();
        this.getStatementHelper().writeLogStatement(getLogStatementEntry(), log);
        return this.getResultSetProxyHelper().updateCachedResultSet(resultSet);
    }

    @Override
    public int executeUpdate() throws SQLException {
        final ParameterMetaData parameterMetaData = getPreparedStatement().getParameterMetaData();
        final int rows = this.getPreparedStatement().executeUpdate();
        this.getStatementHelper().writeLogStatement(getLogStatementEntry(), log);
        return rows;
    }

    @Override
    public boolean execute() throws SQLException {
        final ParameterMetaData parameterMetaData = getPreparedStatement().getParameterMetaData();
        final boolean success = this.getPreparedStatement().execute();
        this.getStatementHelper().writeLogStatement(getLogStatementEntry(), log);
        return success;
    }

    @Override
    public void addBatch() throws SQLException {
        this.getPreparedStatement().addBatch();
        this.getStatementHelper().logAddBatch(getLogStatementEntry());
    }

    protected LogStatementEntry getLogStatementEntry() {
        final ArrayList<String> parameters = getSqlParameters(this.getPreparedParameters().values());
        return new LogStatementEntry(this.getSql(), parameters);
    }

    protected ArrayList<String> getSqlParameters(Collection<PreparedParameter> values) {
        final ArrayList<String> parameters = new ArrayList<>();
        for (PreparedParameter parameter : values) {
            parameters.add(parameter.toString());
        }
        return parameters;
    }
}
