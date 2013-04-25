package lt.nsg.jdbcglass.statement;

import lt.nsg.jdbcglass.statement.proxy.CallableStatementProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.CallableStatement;
import java.sql.Connection;

public class LoggingCallableStatementProxy extends CallableStatementProxy {
    public LoggingCallableStatementProxy(CallableStatement callableStatement, Connection connection, String sql, Logger log) {
        super(callableStatement, connection, sql, log);
    }

    public LoggingCallableStatementProxy(CallableStatement callableStatement, Connection connection, String sql) {
        this(callableStatement, connection, sql, LoggerFactory.getLogger(LoggingCallableStatementProxy.class));
    }
}
