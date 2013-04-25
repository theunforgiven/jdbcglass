package lt.nsg.jdbcglass.statement.parameter;

public class CallableParameter extends PreparedParameter {
    private final String parameterName;

    protected CallableParameter(String parameterName, String parameterValue) {
        super(parameterValue);
        this.parameterName = parameterName;
    }

    @Override
    public String interpolateString(String sql) {
        final String toReplace = ":" + parameterName;
        return sql.replaceAll(toReplace, this.getFormattedStringValue());
    }

    public static CallableParameter UnloggableForParameter(String parameterName) {
        return new CallableParameter(parameterName, UnloggableParameterValue);
    }

    public static CallableParameter ForParameter(String parameterName, Object parameterValue) {
        return new CallableParameter(parameterName, parameterValue.toString());
    }
}
