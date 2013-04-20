package lt.nsg.jdbcglass.statement;

import java.sql.*;

public abstract class AbstractStatementProxy implements Statement {
    private final Statement statement;

    protected final Statement getStatement() {
        return statement;
    }

    protected AbstractStatementProxy(Statement statement) {
        this.statement = statement;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return statement.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return statement.isWrapperFor(iface);
    }

    @Override
    public void close() throws SQLException {
        getStatement().close();
    }

    @Override
    public int getMaxFieldSize() throws SQLException {
        return getStatement().getMaxFieldSize();
    }

    @Override
    public void setMaxFieldSize(int max) throws SQLException {
        getStatement().setMaxFieldSize(max);
    }

    @Override
    public int getMaxRows() throws SQLException {
        return getStatement().getMaxRows();
    }

    @Override
    public void setMaxRows(int max) throws SQLException {
        getStatement().setMaxRows(max);
    }

    @Override
    public void setEscapeProcessing(boolean enable) throws SQLException {
        getStatement().setEscapeProcessing(enable);
    }

    @Override
    public int getQueryTimeout() throws SQLException {
        return getStatement().getQueryTimeout();
    }

    @Override
    public void setQueryTimeout(int seconds) throws SQLException {
        getStatement().setQueryTimeout(seconds);
    }

    @Override
    public void cancel() throws SQLException {
        getStatement().cancel();
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return getStatement().getWarnings();
    }

    @Override
    public void clearWarnings() throws SQLException {
        getStatement().clearWarnings();
    }

    @Override
    public void setCursorName(String name) throws SQLException {
        getStatement().setCursorName(name);
    }


    @Override
    public int getResultSetHoldability() throws SQLException {
        return getStatement().getResultSetHoldability();
    }

    @Override
    public boolean isClosed() throws SQLException {
        return getStatement().isClosed();
    }

    @Override
    public void setPoolable(boolean poolable) throws SQLException {
        getStatement().setPoolable(poolable);
    }

    @Override
    public boolean isPoolable() throws SQLException {
        return getStatement().isPoolable();
    }

    @Override
    public void closeOnCompletion() throws SQLException {
        getStatement().closeOnCompletion();
    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        return getStatement().isCloseOnCompletion();
    }

    @Override
    public abstract Connection getConnection() throws SQLException;

    @Override
    public boolean getMoreResults(int current) throws SQLException {
        return getStatement().getMoreResults(current);
    }

    @Override
    public abstract ResultSet getGeneratedKeys() throws SQLException;

    @Override
    public int getUpdateCount() throws SQLException {
        return getStatement().getUpdateCount();
    }

    @Override
    public boolean getMoreResults() throws SQLException {
        return getStatement().getMoreResults();
    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {
        getStatement().setFetchDirection(direction);
    }

    @Override
    public int getFetchDirection() throws SQLException {
        return getStatement().getFetchDirection();
    }

    @Override
    public void setFetchSize(int rows) throws SQLException {
        getStatement().setFetchSize(rows);
    }

    @Override
    public int getFetchSize() throws SQLException {
        return getStatement().getFetchSize();
    }

    @Override
    public int getResultSetConcurrency() throws SQLException {
        return getStatement().getResultSetConcurrency();
    }

    @Override
    public int getResultSetType() throws SQLException {
        return getStatement().getResultSetType();
    }

    @Override
    public abstract ResultSet executeQuery(String sql) throws SQLException;

    @Override
    public abstract int executeUpdate(String sql) throws SQLException;

    @Override
    public abstract boolean execute(String sql) throws SQLException;

    @Override
    public abstract ResultSet getResultSet() throws SQLException;

    @Override
    public abstract void addBatch(String sql) throws SQLException;

    @Override
    public abstract void clearBatch() throws SQLException;

    @Override
    public abstract int[] executeBatch() throws SQLException;

    @Override
    public abstract int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException;

    @Override
    public abstract int executeUpdate(String sql, int[] columnIndexes) throws SQLException;

    @Override
    public abstract int executeUpdate(String sql, String[] columnNames) throws SQLException;

    @Override
    public abstract boolean execute(String sql, int autoGeneratedKeys) throws SQLException;

    @Override
    public abstract boolean execute(String sql, int[] columnIndexes) throws SQLException;

    @Override
    public abstract boolean execute(String sql, String[] columnNames) throws SQLException;
}