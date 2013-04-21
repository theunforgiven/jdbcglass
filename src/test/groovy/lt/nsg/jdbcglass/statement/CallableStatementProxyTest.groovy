package lt.nsg.jdbcglass.statement

import lt.nsg.jdbcglass.LogbackCapturingSpecification
import spock.lang.Subject

import java.sql.CallableStatement
import java.sql.Connection

class CallableStatementProxyTest extends LogbackCapturingSpecification {
    private static final String PREPARED_SQL = "SQL"
    private Connection connection = Mock(Connection)
    private CallableStatement callableStatement = Mock(CallableStatement)

    @Subject
    def proxied = new CallableStatementProxy(callableStatement, connection, PREPARED_SQL)

    def "should format parameters"() {
        given:
        def foo = "a"
        def bar = "b"
        def sql = """SELECT * FROM WOOT WHERE FOO = :foo AND BAR = :bar"""
        proxied = new CallableStatementProxy(callableStatement, connection, sql)

        when:
        proxied.setString("foo", foo)
        proxied.setString("bar", bar)
        proxied.execute()

        then:
        logOutput.first() == """SELECT * FROM WOOT WHERE FOO = '${foo}' AND BAR = '${bar}'"""
    }
}
