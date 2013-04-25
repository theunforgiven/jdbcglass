package lt.nsg.jdbcglass.statement.entry;

import lt.nsg.jdbcglass.statement.parameter.PreparedParameter;

import java.util.Collection;

public class ParameterizedStatementLogEntry extends StatementLogEntry {
    private final String formattedSql;

    public ParameterizedStatementLogEntry(String sql, Collection<PreparedParameter> parameters) {
        super("");
        this.formattedSql = formatSql(sql, parameters);
    }

    @Override
    public String getSql() {
        return this.formattedSql;
    }

    private String formatSql(String sql, Collection<PreparedParameter> parameters) {
        if (parameters.size() == 0) {
            return sql;
        }
        String interpolatedSql = sql;
        for (PreparedParameter parameter : parameters) {
            if (parameter == PreparedParameter.Unloggable) {
                continue;
            }
            interpolatedSql = parameter.interpolateString(interpolatedSql);
        }
        return interpolatedSql;
    }
}
