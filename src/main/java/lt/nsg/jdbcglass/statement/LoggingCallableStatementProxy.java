package lt.nsg.jdbcglass.statement;

import lt.nsg.jdbcglass.statement.proxy.CallableStatementProxy;

import java.sql.CallableStatement;
import java.sql.Connection;

public class LoggingCallableStatementProxy extends CallableStatementProxy {
    public LoggingCallableStatementProxy(CallableStatement callableStatement, Connection connection, String sql) {
        super(callableStatement, connection, sql);
    }
}
