package lt.nsg.jdbcglass.resultset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ResultSetProxy extends AbstractResultSetProxy {
    private final Logger log = LoggerFactory.getLogger(ResultSetProxy.class);
    private final ResultSetHelper resultSetHelper = new ResultSetHelper();

    public ResultSetProxy(ResultSet resultSet, Statement originalStatement) {
        super(resultSet, originalStatement);
    }

    @Override
    public boolean next() throws SQLException {
        final boolean hasNextRow = super.next();
        if (hasNextRow) {
            this.resultSetHelper.logResultSetRow(log, this.getResultSet());
        }
        return hasNextRow;
    }
}
