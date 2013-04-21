package lt.nsg.jdbcglass.resultset;

import lt.nsg.jdbcglass.FormatableValue;

public class ResultSetColumn extends FormatableValue {
    private final String value;

    public ResultSetColumn(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
