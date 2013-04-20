package lt.nsg.jdbcglass.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ResultSetProxy extends AbstractResultSetProxy {
    private final Statement originalStatement;

    public ResultSetProxy(ResultSet resultSet, Statement originalStatement) {
        super(resultSet);
        this.originalStatement = originalStatement;
    }

    @Override
    public Statement getStatement() throws SQLException {
        return this.originalStatement;
    }
}
