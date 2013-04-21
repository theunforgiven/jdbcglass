package lt.nsg.jdbcglass.statement;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.Map;
import java.util.TreeMap;

public class CallableStatementProxy extends AbstractCallableStatementProxy {
    public CallableStatementProxy(CallableStatement callableStatement, Connection connection, String sql) {
        super(callableStatement, connection, sql);
    }

    @Override
    protected LogStatementEntry getLogStatementEntry() {
        final TreeMap<String, PreparedParameter> callableParameters = this.getCallableParameters();
        if (callableParameters.size() > 0) {
            return new LogStatementEntry(interpolateNamedParameters(callableParameters));
        }
        return super.getLogStatementEntry();
    }

    private String interpolateNamedParameters(TreeMap<String, PreparedParameter> callableParameters) {
        String parameterInterpolated = getSql();

        for (Map.Entry<String, PreparedParameter> entry : callableParameters.entrySet()) {
            final String parameterName = entry.getKey();
            final String parameterValue = entry.getValue().toString();
            parameterInterpolated = parameterInterpolated.replaceAll(":".concat(parameterName), parameterValue);
        }

        return parameterInterpolated;
    }
}
