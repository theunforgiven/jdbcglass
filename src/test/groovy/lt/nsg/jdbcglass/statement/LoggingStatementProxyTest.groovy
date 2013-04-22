package lt.nsg.jdbcglass.statement

import lt.nsg.jdbcglass.LogbackCapturingSpecification
import lt.nsg.jdbcglass.resultset.LoggingResultSetProxy

import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement

public class LoggingStatementProxyTest extends LogbackCapturingSpecification {
    def conn = Mock(Connection)
    def statement = Mock(Statement)
    def proxied = new LoggingStatementProxy(statement, conn)

    def "can log a statement execute query call"() {
        given:
        def sql = "SELECT * FROM TestTable"

        when:
        proxied.executeQuery(sql)

        then:
        logOutput.first().contains(sql)
        1 * statement.executeQuery(sql)
    }

    def "can log a statement execute call"() {
        given:
        def sql = "INSERT INTO TestTable (name) VALUES ('nick')"

        when:
        proxied.execute(sql)

        then:
        logOutput.first().contains(sql)
        1 * statement.execute(sql)
    }

    def "can log a statement execute update call"() {
        given:
        def sql = "INSERT INTO TestTable (name) VALUES ('nick')"

        when:
        proxied.executeUpdate(sql)

        then:
        logOutput.first().contains(sql)
        1 * statement.executeUpdate(sql)
    }

    def "can log a statement execute batch call"() {
        given:
        def sql = "INSERT INTO TestTable (name) VALUES ('barry')"
        def sql2 = "INSERT INTO TestTable (name) VALUES ('marty')"

        when:
        def stmt = proxied
        stmt.addBatch(sql)
        stmt.addBatch(sql2)
        stmt.executeBatch()
        stmt.close()

        then:
        logOutput.first().contains(sql)
        logOutput.last().contains(sql2)
    }

    def "clears log messages when a batch is cleared"() {
        given:
        def sql = "INSERT INTO TestTable (name) VALUES ('barry')"
        def sql2 = "INSERT INTO TestTable (name) VALUES ('marty')"

        when:
        proxied.addBatch(sql)
        proxied.clearBatch()
        proxied.addBatch(sql2)
        proxied.executeBatch()
        proxied.close()

        then:
        logOutput.first().contains(sql2)
        logOutput.size() == 1
    }

    def "after a batch is executed the batch is cleared"() {
        given:
        def sql = "INSERT INTO TestTable (name) VALUES ('barry')"
        def sql2 = "INSERT INTO TestTable (name) VALUES ('marty')"

        when:
        def stmt = proxied
        stmt.addBatch(sql)
        stmt.executeBatch()
        stmt.addBatch(sql2)
        stmt.executeBatch()

        then:
        1 * statement.addBatch(sql)
        1 * statement.executeBatch()
        1 * statement.addBatch(sql2)
        1 * statement.executeBatch()
        and:
        logOutput.size() == 2
    }

    def "proxies a result set"() {
        given:
        def sql = "SELECT * FROM TestTable"
        def resultSet = Mock(ResultSet)
        statement.executeQuery(_) >> resultSet
        statement.getResultSet() >> resultSet

        when:
        def returnedResultSet = proxied.executeQuery(sql)

        then:
        logOutput.size() == 1
        returnedResultSet instanceof LoggingResultSetProxy
        returnedResultSet.statement.is(proxied)
        proxied.resultSet.is(returnedResultSet)
    }

    def "resultSet always returns the last resultset"() {
        given:
        statement.executeQuery(_) >>> [Mock(ResultSet), Mock(ResultSet)]

        when:
        def firstReturnedResultSet = proxied.executeQuery("SELECT * FROM TestTable")
        def secondReturnedResultSet = proxied.executeQuery("SELECT * FROM TestTable")

        then:
        firstReturnedResultSet != secondReturnedResultSet

        and:
        logOutput.size() == 2
    }

    def "resultset get generated keys is proxied"() {
        given:
        def resultSet = Mock(ResultSet)
        statement.getGeneratedKeys() >> resultSet

        when:
        def proxiedRs = proxied.getGeneratedKeys()

        then:
        proxiedRs.getStatement().is(proxied)
    }
}

