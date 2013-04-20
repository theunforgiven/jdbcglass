package lt.nsg.jdbcglass.statement;

import java.sql.ParameterMetaData;

public class LogStatementEntry {
    private final ParameterMetaData parameterMetaData;
    private final String sql;

    public LogStatementEntry(ParameterMetaData parameterMetaData, String sql) {
        this.parameterMetaData = parameterMetaData;
        this.sql = sql;
    }

    public ParameterMetaData getParameterMetaData() {
        return parameterMetaData;
    }

    public String getSql() {
        return sql;
    }
}
