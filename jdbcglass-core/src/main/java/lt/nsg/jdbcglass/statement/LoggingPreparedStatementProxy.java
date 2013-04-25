package lt.nsg.jdbcglass.statement;

import lt.nsg.jdbcglass.statement.proxy.PreparedStatementProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoggingPreparedStatementProxy extends PreparedStatementProxy {
    public LoggingPreparedStatementProxy(PreparedStatement statement, Connection connection, String sql, Logger log) {
        super(statement, connection, sql, log);
    }

    public LoggingPreparedStatementProxy(PreparedStatement statement, Connection connection, String sql) {
        this(statement, connection, sql, LoggerFactory.getLogger(LoggingPreparedStatementProxy.class));
    }

    @Override
    public ResultSet executeQuery() throws SQLException {
        ResultSet resultSet = super.executeQuery();
        this.getStatementLogWriter().writeLogStatement(this.getSql(), this.getPreparedParameters());
        return resultSet;
    }

    @Override
    public int executeUpdate() throws SQLException {
        final int rows = super.executeUpdate();
        this.getStatementLogWriter().writeLogStatement(this.getSql(), this.getPreparedParameters());
        return rows;
    }

    @Override
    public boolean execute() throws SQLException {
        final boolean success = super.execute();
        this.getStatementLogWriter().writeLogStatement(this.getSql(), this.getPreparedParameters());
        return success;
    }

    @Override
    public void addBatch() throws SQLException {
        super.addBatch();
        this.getBatchLogger().logAddBatch(this.getSql(), this.getPreparedParameters());
    }
}
