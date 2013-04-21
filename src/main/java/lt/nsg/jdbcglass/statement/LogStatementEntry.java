package lt.nsg.jdbcglass.statement;

import java.util.ArrayList;

public class LogStatementEntry {
    private final String formattedSql;

    public LogStatementEntry(String sql, ArrayList<String> parameters) {
        this.formattedSql = formatSql(sql, parameters);
    }

    public LogStatementEntry(String sql) {
        this.formattedSql = sql;
    }

    public String getSql() {
        return formattedSql;
    }

    private String formatSql(String sql, ArrayList<String> parameters) {
        if (parameters.size() == 0) {
            return sql;
        }

        final StringBuilder sb = new StringBuilder();
        int position = 0;
        for (String parameter : parameters) {
            final int placeHolder = sql.indexOf("?", position);
            sb.append(sql.substring(position, placeHolder));
            sb.append("'").append(parameter).append("'");
            position = placeHolder + 1;
        }
        return sb.toString();
    }
}
