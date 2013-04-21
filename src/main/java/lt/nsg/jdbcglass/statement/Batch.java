package lt.nsg.jdbcglass.statement;

import java.util.ArrayList;
import java.util.List;

public class Batch extends ArrayList<LogStatementEntry> {
    public Batch() {
    }

    public Batch(List<LogStatementEntry> batches) {
        super(batches);
    }
}
