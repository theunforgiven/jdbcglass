package lt.nsg.jdbcglass

import lt.nsg.jdbcglass.util.InMemoryDbUtil
import org.h2.jdbcx.JdbcDataSource

import java.sql.Connection
import java.sql.Statement

public class StatementProxyTest extends LogbackCapturingSpecification {
    private static Connection connection

    def setup() {
        def ds = new JdbcDataSource()
        ds.setURL(InMemoryDbUtil.testDbUrl)
        connection = ds.getConnection()
        createTestTable()
    }

    def cleanup() {
        connection.close()
    }

    def "can log a statement execute query call"() {
        given:
        def sql = "SELECT * FROM TestTable"

        when:
        def stmt = proxyStatement(connection.createStatement())
        stmt.executeQuery(sql)
        stmt.close()

        then:
        logOutput.first().contains(sql)
    }

    def "can log a statement execute call"() {
        given:
        def sql = "INSERT INTO TestTable (name) VALUES ('nick')"

        when:
        def stmt = proxyStatement(connection.createStatement())
        stmt.execute(sql)
        stmt.close()

        then:
        logOutput.first().contains(sql)
    }

    def "can log a statement execute update call"() {
        given:
        def sql = "INSERT INTO TestTable (name) VALUES ('nick')"

        when:
        def stmt = proxyStatement(connection.createStatement())
        stmt.executeUpdate(sql)
        stmt.close()

        then:
        logOutput.first().contains(sql)
    }

    def "can log a statement execute batch call"() {
        given:
        def sql = "INSERT INTO TestTable (name) VALUES ('barry')"
        def sql2 = "INSERT INTO TestTable (name) VALUES ('marty')"

        when:
        def stmt = proxyStatement(connection.createStatement())
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
        def stmt = proxyStatement(connection.createStatement())
        stmt.addBatch(sql)
        stmt.clearBatch()
        stmt.addBatch(sql2)
        stmt.executeBatch()
        stmt.close()

        then:
        logOutput.first().contains(sql2)
        logOutput.size() == 1
    }

    def "after a batch is executed the batch is cleared"() {
        given:
        def sql = "INSERT INTO TestTable (name) VALUES ('barry')"
        def sql2 = "INSERT INTO TestTable (name) VALUES ('marty')"

        when:
        def stmt = proxyStatement(connection.createStatement())
        stmt.addBatch(sql)
        stmt.executeBatch()
        stmt.addBatch(sql2)
        stmt.executeBatch()
        stmt.close()

        then:
        logOutput.size() == 2
    }

    def "proxies a result set"() {
        given:
        createTestTableData("person a")
        createTestTableData("person b")

        when:
        def stmt = proxyStatement(connection.createStatement())
        def returnedResultSet = stmt.executeQuery("SELECT * FROM TestTable")

        then:
        logOutput.size() == 1
        returnedResultSet instanceof ResultSetProxy
        returnedResultSet.statement.is(stmt)
        stmt.resultSet.is(returnedResultSet)

        cleanup:
        stmt.close()
    }

    def "resultSet always returns the last resultset"() {
        given:
        createTestTableData("person a")
        createTestTableData("person b")

        when:
        def stmt = proxyStatement(connection.createStatement())
        def firstReturnedResultSet = stmt.executeQuery("SELECT * FROM TestTable")
        def secondReturnedResultSet = stmt.executeQuery("SELECT * FROM TestTable")

        then:
        logOutput.size() == 2
        firstReturnedResultSet != secondReturnedResultSet

        cleanup:
        stmt.close()
    }

    private static Statement proxyStatement(Statement stmt) {
        new StatementProxy(stmt, connection)
    }

    private static void createTestTable() {
        executeSql("CREATE TABLE TestTable(id BIGINT IDENTITY PRIMARY KEY, name VARCHAR(255) DEFAULT '')")
    }

    private static void createTestTableData(String name) {
        executeSql("INSERT INTO TestTable (name) VALUES ('${name}')")
    }

    private static void executeSql(String sql) {
        def stmt = connection.createStatement()
        stmt.execute(sql)
        stmt.close()
    }
}

