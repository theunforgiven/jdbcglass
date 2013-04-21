package lt.nsg.jdbcglass.resultset

import lt.nsg.jdbcglass.LogbackCapturingSpecification

import java.sql.ResultSet
import java.sql.ResultSetMetaData
import java.sql.Statement

public class ResultSetProxyTest extends LogbackCapturingSpecification {
    private ResultSet resultSet = Mock(ResultSet)
    private Statement statement = Mock(Statement)

    def proxied = new ResultSetProxy(resultSet, statement)

    def "should proxy the statement"() {
        expect:
        proxied.statement.is(statement)
    }

    def "should log a row when next is successfully called"() {
        given:
        def metaData = Mock(ResultSetMetaData)
        metaData.getColumnCount() >> 2
        resultSet.getMetaData() >> metaData
        resultSet.next() >> true
        resultSet.getObject(1) >> "col1"
        resultSet.getObject(2) >> "col2"

        when:
        def result = proxied.next()

        then:
        result
        logOutput.first() == " {'col1','col2'}"
    }

    def "should not log a row when next is unsuccessfully called"() {
        given:
        resultSet.next() >> false

        when:
        def result = proxied.next()

        then:
        !result
        logOutput.isEmpty()
    }
}
