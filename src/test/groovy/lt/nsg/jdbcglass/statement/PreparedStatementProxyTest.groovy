package lt.nsg.jdbcglass.statement

import lt.nsg.jdbcglass.LogbackCapturingSpecification
import lt.nsg.jdbcglass.resultset.ResultSetProxy
import spock.lang.Subject

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class PreparedStatementProxyTest extends LogbackCapturingSpecification {
    private static final String PREPARED_SQL = "SQL"
    private Connection connection = Mock(Connection)
    private PreparedStatement preparedStatement = Mock(PreparedStatement)

    @Subject
    def proxied = new PreparedStatementProxy(preparedStatement, connection, PREPARED_SQL)

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
        logOutput.first() == PREPARED_SQL
    }

    def "logs query parameter meta data and sql when execute update is called"() {
        when:
        proxied.executeUpdate()

        then:
        logOutput.first() == PREPARED_SQL
        1 * preparedStatement.executeUpdate()
    }

    def "logs query parameter meta data and sql when execute is called"() {
        when:
        proxied.execute()

        then:
        logOutput.first() == PREPARED_SQL
        1 * preparedStatement.execute()
    }

    def "logs query parameter meta data and sql when add batch is called"() {
        when:
        proxied.addBatch()

        then:
        1 * preparedStatement.getParameterMetaData()

        and:
        logOutput.size() == 0

        when:
        proxied.addBatch()

        then:
        1 * preparedStatement.getParameterMetaData()

        and:
        logOutput.size() == 0

        when:
        proxied.executeBatch()

        then:
        1 * preparedStatement.executeBatch()

        and:
        logOutput.first() == PREPARED_SQL
        logOutput.last() == PREPARED_SQL
        logOutput.size() == 2
    }


}
