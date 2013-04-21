package lt.nsg.jdbcglass.resultset;

public class ResultSetColumn {
    private final String value;

    public ResultSetColumn(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
