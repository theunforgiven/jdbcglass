package lt.nsg.jdbcglass.statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PreparedStatementProxy extends AbstractPreparedStatementProxy {
    private final static Logger log = LoggerFactory.getLogger(PreparedStatementProxy.class);

    public PreparedStatementProxy(PreparedStatement statement, Connection connection, String sql) {
        super(statement, connection, sql);
    }

    @Override
    public ResultSet executeQuery() throws SQLException {
        ResultSet resultSet = super.executeQuery();
        this.getStatementHelper().writeLogStatement(this.getSql(), this.getPreparedParameters(), log);
        return resultSet;
    }

    @Override
    public int executeUpdate() throws SQLException {
        final int rows = super.executeUpdate();
        this.getStatementHelper().writeLogStatement(this.getSql(), this.getPreparedParameters(), log);
        return rows;
    }

    @Override
    public boolean execute() throws SQLException {
        final boolean success = super.execute();
        this.getStatementHelper().writeLogStatement(this.getSql(), this.getPreparedParameters(), log);
        return success;
    }

    @Override
    public void addBatch() throws SQLException {
        super.addBatch();
        this.getBatchLogger().logAddBatch(this.getSql(), this.getPreparedParameters());
    }
}
