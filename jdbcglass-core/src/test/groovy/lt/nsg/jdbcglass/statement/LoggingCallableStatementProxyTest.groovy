package lt.nsg.jdbcglass.statement

import lt.nsg.jdbcglass.LogbackCapturingSpecification
import spock.lang.Subject

import java.sql.CallableStatement
import java.sql.Connection

class LoggingCallableStatementProxyTest extends LogbackCapturingSpecification {
    private final String firstParameter = "a"
    private final String secondParameter = "b"
    private final String PREPARED_SQL = "SELECT * FROM WOOT WHERE FOO = :foo AND BAR = :bar"

    private Connection connection = Mock(Connection)
    private CallableStatement callableStatement = Mock(CallableStatement)

    @Subject
    LoggingCallableStatementProxy proxied

    def setup() {
        proxied = new LoggingCallableStatementProxy(callableStatement, connection, PREPARED_SQL, capturingLogger)
    }

    def "should format parameters"() {
        when:
        proxied.setString("foo", firstParameter)
        proxied.setString("bar", secondParameter)
        proxied.execute()

        then:
        logOutput.first() == """SELECT * FROM WOOT WHERE FOO = '${firstParameter}' AND BAR = '${secondParameter}'"""
    }
}
