package lt.nsg.jdbcglass.statement;

public class LogStatementEntry {
    private final String formattedSql;

    public LogStatementEntry(String sql) {
        this.formattedSql = sql;
    }

    public String getSql() {
        return formattedSql;
    }
}
