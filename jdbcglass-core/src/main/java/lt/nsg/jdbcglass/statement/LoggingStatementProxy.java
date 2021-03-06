package lt.nsg.jdbcglass.statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoggingStatementProxy extends AbstractLoggingStatementProxy {
    public LoggingStatementProxy(Statement statement, Connection connection, Logger logger) {
        super(statement, connection, logger);

    }

    public LoggingStatementProxy(Statement statement, Connection connection) {
        this(statement, connection, LoggerFactory.getLogger(LoggingStatementProxy.class));
    }

    @Override
    public int executeUpdate(String sql) throws SQLException {
        int rows = super.executeUpdate(sql);
        statementLogWriter.writeLogStatement(sql);
        return rows;
    }

    @Override
    public boolean execute(String sql) throws SQLException {
        boolean result = super.execute(sql);
        statementLogWriter.writeLogStatement(sql);
        return result;
    }

    @Override
    public int[] executeBatch() throws SQLException {
        int[] rows = super.executeBatch();
        batchLogger.writeLogBatch(statementLogWriter);
        return rows;
    }

    @Override
    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        int rows = super.executeUpdate(sql, autoGeneratedKeys);
        statementLogWriter.writeLogStatement(sql);
        return rows;
    }

    @Override
    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
        int rows = super.executeUpdate(sql, columnIndexes);
        statementLogWriter.writeLogStatement(sql);
        return rows;
    }

    @Override
    public int executeUpdate(String sql, String[] columnNames) throws SQLException {
        int rows = super.executeUpdate(sql, columnNames);
        statementLogWriter.writeLogStatement(sql);
        return rows;
    }

    @Override
    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        boolean result = super.execute(sql, autoGeneratedKeys);
        statementLogWriter.writeLogStatement(sql);
        return result;
    }

    @Override
    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        boolean result = super.execute(sql, columnIndexes);
        statementLogWriter.writeLogStatement(sql);
        return result;
    }

    @Override
    public boolean execute(String sql, String[] columnNames) throws SQLException {
        boolean result = super.execute(sql, columnNames);
        statementLogWriter.writeLogStatement(sql);
        return result;
    }


    @Override
    public ResultSet executeQuery(String sql) throws SQLException {
        ResultSet resultSet = super.executeQuery(sql);
        statementLogWriter.writeLogStatement(sql);
        return resultSet;
    }

}