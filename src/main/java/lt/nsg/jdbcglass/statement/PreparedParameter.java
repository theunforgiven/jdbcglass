package lt.nsg.jdbcglass.statement;

public class PreparedParameter {
    public static final PreparedParameter Unloggable = new PreparedParameter(new Object()) {
        @Override
        public String toString() {
            return "Unloggable";
        }
    };

    private final Object parameterValue;

    public PreparedParameter(Object parameterValue) {
        this.parameterValue = parameterValue;
    }

    @Override
    public String toString() {
        return this.parameterValue.toString();
    }
}
