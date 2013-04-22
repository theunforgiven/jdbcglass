package lt.nsg.jdbcglass.statement.entry;

public class StatementLogEntry {
    private final String formattedSql;

    public StatementLogEntry(String sql) {
        this.formattedSql = sql;
    }

    public String getSql() {
        return formattedSql;
    }
}
