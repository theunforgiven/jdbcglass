package lt.nsg.jdbcglass.statement;

public class CallableParameter extends PreparedParameter {
    private final String parameterName;

    public CallableParameter(String parameterName, Object parameterValue) {
        super(parameterValue);
        this.parameterName = parameterName;
    }

    public CallableParameter(String parameterName) {
        super("Unloggable");
        this.parameterName = parameterName;
    }

    @Override
    public String interpolateString(String sql) {
        final String toReplace = ":" + parameterName;
        return sql.replaceAll(toReplace, this.getFormattedStringValue());
    }

    public static CallableParameter UnloggableForParameter(String parameterName) {
        return new CallableParameter(parameterName);
    }
}
