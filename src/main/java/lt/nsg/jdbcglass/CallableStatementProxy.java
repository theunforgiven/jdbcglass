package lt.nsg.jdbcglass;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class CallableStatementProxy extends AbstractCallableStatementProxy {
    private final Connection connection;

    public CallableStatementProxy(CallableStatement callableStatement, Connection connection) {
        super(callableStatement);
        this.connection = connection;
    }

    public Connection getConnection() throws SQLException {
        return connection;
    }
}
