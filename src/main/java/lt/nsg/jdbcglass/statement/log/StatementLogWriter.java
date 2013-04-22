package lt.nsg.jdbcglass.statement.log;

import lt.nsg.jdbcglass.statement.entry.ParameterizedStatementLogEntry;
import lt.nsg.jdbcglass.statement.entry.StatementLogEntry;
import lt.nsg.jdbcglass.statement.parameter.PreparedParameter;
import org.slf4j.Logger;

import java.util.Collection;

public class StatementLogWriter {
    public void writeLogStatement(String sql, Logger log) {
        StatementLogEntry entry = new StatementLogEntry(sql);
        writeLogEntry(entry, log);
    }

    public void writeLogStatement(String sql, Collection<PreparedParameter> parameters, Logger log) {
        ParameterizedStatementLogEntry entry = new ParameterizedStatementLogEntry(sql, parameters);
        writeLogEntry(entry, log);
    }

    public void writeLogEntry(StatementLogEntry logEntry, Logger log) {
        log.info(logEntry.getSql());
    }
}