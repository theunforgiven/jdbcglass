package lt.nsg.jdbcglass.statement.log;

import lt.nsg.jdbcglass.statement.entry.ParameterizedStatementLogEntry;
import lt.nsg.jdbcglass.statement.entry.StatementLogEntry;
import lt.nsg.jdbcglass.statement.parameter.PreparedParameter;
import org.slf4j.Logger;

import java.util.Collection;

public class StatementLogWriter {
    private final Logger log;

    public StatementLogWriter(Logger log) {
        this.log = log;
    }

    public void writeLogStatement(String sql) {
        StatementLogEntry entry = new StatementLogEntry(sql);
        writeLogEntry(entry);
    }

    public void writeLogStatement(String sql, Collection<PreparedParameter> parameters) {
        ParameterizedStatementLogEntry entry = new ParameterizedStatementLogEntry(sql, parameters);
        writeLogEntry(entry);
    }

    public void writeLogEntry(StatementLogEntry logEntry) {
        log.info(logEntry.getSql());
    }
}