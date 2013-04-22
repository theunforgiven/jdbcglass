package lt.nsg.jdbcglass.statement.log;

import lt.nsg.jdbcglass.statement.entry.StatementLogEntry;

import java.util.ArrayList;
import java.util.List;

public class Batch extends ArrayList<StatementLogEntry> {
    public Batch() {
    }

    public Batch(List<StatementLogEntry> batches) {
        super(batches);
    }
}
