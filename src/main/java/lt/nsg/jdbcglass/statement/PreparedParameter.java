package lt.nsg.jdbcglass.statement;


import lt.nsg.jdbcglass.FormatableValue;

public class PreparedParameter extends FormatableValue {
    public static final PreparedParameter Unloggable = new PreparedParameter("Unloggable");

    private final Object parameterValue;

    public PreparedParameter(Object parameterValue) {
        this.parameterValue = parameterValue;
    }

    @Override
    public String toString() {
        return this.parameterValue.toString();
    }

    public String interpolateString(String sql) {
        return sql.replaceFirst("\\?", this.getFormattedStringValue());
    }
}
