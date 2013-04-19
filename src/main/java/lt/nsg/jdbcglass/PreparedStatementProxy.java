package lt.nsg.jdbcglass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PreparedStatementProxy extends AbstractPreparedStatementProxy{
    private final Connection connection;

    public PreparedStatementProxy(PreparedStatement statement, Connection connection) {
        super(statement);
        this.connection = connection;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return connection;
    }
}
