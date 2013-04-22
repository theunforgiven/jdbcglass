package lt.nsg.jdbcglass.statement.parameter;


import lt.nsg.jdbcglass.FormatableValue;

public class PreparedParameter extends FormatableValue {
    protected static final String UnloggableParameterValue = "Unloggable";
    public static final PreparedParameter Unloggable = new PreparedParameter(UnloggableParameterValue);

    private final String parameterValue;

    protected PreparedParameter(Object parameterValue) {
        this.parameterValue = parameterValue.toString();
    }

    @Override
    public String toString() {
        return this.parameterValue;
    }

    public String interpolateString(String sql) {
        return sql.replaceFirst("\\?", this.getFormattedStringValue());
    }

    public static PreparedParameter ForParameter(Object parameterValue) {
        return new PreparedParameter(parameterValue.toString());
    }
}
