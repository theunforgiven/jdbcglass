package lt.nsg.jdbcglass.resultset;

import org.slf4j.Logger;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResultSetHelper {
    public void logResultSetRow(Logger log, ResultSet resultSet) throws SQLException {
        final List<ResultSetColumn> columns = getResultSetColumns(resultSet);
        final String message = createLogMessage(columns);
        log.info(" {}", message);
    }

    private String createLogMessage(List<ResultSetColumn> columns) {
        if (columns.size() == 0) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (ResultSetColumn column : columns) {
            sb.append("'")
                    .append(column.toString())
                    .append("'")
                    .append(",");
        }
        sb.replace(sb.length() - 1, sb.length(), "}");
        return sb.toString();
    }

    private List<ResultSetColumn> getResultSetColumns(ResultSet resultSet) throws SQLException {
        final ResultSetMetaData md = resultSet.getMetaData();
        final int columnCount = md.getColumnCount();
        final ArrayList<ResultSetColumn> columns = new ArrayList<>();
        for (int i = 1; i <= columnCount; i++) {
            final String column = resultSet.getObject(i).toString();
            columns.add(new ResultSetColumn(column));
        }
        return columns;
    }
}
