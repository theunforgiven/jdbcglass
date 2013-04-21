package lt.nsg.jdbcglass.statement;

import java.sql.CallableStatement;
import java.sql.Connection;

public class CallableStatementProxy extends AbstractCallableStatementProxy {
    public CallableStatementProxy(CallableStatement callableStatement, Connection connection, String sql) {
        super(callableStatement, connection, sql);
    }
}
