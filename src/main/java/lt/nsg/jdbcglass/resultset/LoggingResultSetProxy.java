package lt.nsg.jdbcglass.resultset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoggingResultSetProxy extends AbstractResultSetProxy {
    private final Logger log = LoggerFactory.getLogger(LoggingResultSetProxy.class);
    private final ResultSetLogger resultSetLogger = new ResultSetLogger();

    public LoggingResultSetProxy(ResultSet resultSet, Statement originalStatement) {
        super(resultSet, originalStatement);
    }

    @Override
    public boolean next() throws SQLException {
        final boolean hasNextRow = super.next();
        if (hasNextRow) {
            this.resultSetLogger.logResultSetRow(log, this.getResultSet());
        }
        return hasNextRow;
    }
}
