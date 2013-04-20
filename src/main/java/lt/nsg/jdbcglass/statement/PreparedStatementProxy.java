package lt.nsg.jdbcglass.statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PreparedStatementProxy extends AbstractPreparedStatementProxy {
    public PreparedStatementProxy(PreparedStatement statement, Connection connection) {
        super(statement, connection);
    }

    @Override
    final public ResultSet executeQuery() throws SQLException {
        return getStatementHelper().hasCachedResultSet(this.getPreparedStatement().executeQuery());
    }
}
