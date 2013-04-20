package lt.nsg.jdbcglass.statement

import lt.nsg.jdbcglass.LogbackCapturingSpecification
import lt.nsg.jdbcglass.resultset.ResultSetProxy

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class PreparedStatementProxyTest extends LogbackCapturingSpecification {
    def connection = Mock(Connection)
    def preparedStatement = Mock(PreparedStatement)
    def proxied = new PreparedStatementProxy(preparedStatement, connection)

    def "proxies a result set"() {
        given:
        def resultSet = Mock(ResultSet)
        preparedStatement.executeQuery() >> resultSet
        preparedStatement.getResultSet() >> resultSet

        when:
        def returnedResultSet = proxied.executeQuery()

        then:
        returnedResultSet instanceof ResultSetProxy
        returnedResultSet.statement.is(proxied)
        proxied.resultSet.is(returnedResultSet)
    }

    def "resultSet always returns the last resultset"() {
        given:
        preparedStatement.executeQuery() >>> [Mock(ResultSet), Mock(ResultSet)]

        when:
        def firstReturnedResultSet = proxied.executeQuery()
        def secondReturnedResultSet = proxied.executeQuery()

        then:
        firstReturnedResultSet != secondReturnedResultSet
    }

    def "logs query parameter meta data and sql when execute query is called"() {
        given:
        preparedStatement.executeQuery() >> Mock(ResultSet)

        when:
        proxied.executeQuery()

        then:
        logOutput.first() == "executeQuery() query parameter metadata"
    }
}
