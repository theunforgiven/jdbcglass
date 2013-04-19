package lt.nsg.jdbcglass

import lt.nsg.jdbcglass.util.InMemoryDbUtil
import org.h2.jdbc.JdbcConnection
import spock.lang.Subject

import java.sql.CallableStatement
import java.sql.Connection
import java.sql.DatabaseMetaData
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Savepoint
import java.sql.Statement

class ConnectionProxyTest extends LogbackCapturingSpecification {
    private Connection connection = Mock(Connection)

    @Subject
    ConnectionProxy proxied

    def setup() {
        proxied = proxyConnection(connection)
        //clear connection opened log
        clearLog()
    }

    def "can log opening a connection"() {
        given:
        proxyConnection(connection)

        expect:
        logOutput.first() == "Connection opened"
    }

    def "can log closing a connection"() {
        given:
        def proxied = proxyConnection(connection)

        when:
        proxied.close()

        then:
        1 * connection.close()
        and:
        logOutput.last() == "Connection closed"
    }

    def "can log starting a transaction"() {
        given:
        connection.getAutoCommit() >>> [false, false, true, true]

        when:
        proxied.setAutoCommit(false)
        proxied.setAutoCommit(true)
        proxied.setAutoCommit(true)
        proxied.setAutoCommit(false)

        then:
        1 * connection.setAutoCommit(false)
        then:
        1 * connection.setAutoCommit(true)
        then:
        1 * connection.setAutoCommit(true)
        then:
        1 * connection.setAutoCommit(false)

        and:
        logOutput.size() == 2
        logOutput.first() == "Transaction started"
        logOutput.last() == "Transaction committed"
    }

    def "can log rolling back a transaction"() {
        given:
        def savepoint = Mock(Savepoint)

        when:
        proxied.rollback()
        proxied.rollback(savepoint)

        then:
        1 * connection.rollback()

        then:
        1 * connection.rollback(savepoint)

        and:
        logOutput.last() == "Transaction rolledback"
        logOutput.first() == "Transaction rolledback"
    }

    def "proxies a statement correctly"() {
        given:
        proxied.createStatement() >> Mock(Statement)
        proxied.createStatement(_, _) >> Mock(Statement)
        proxied.createStatement(_, _, _) >> Mock(Statement)

        when:
        def stmt = proxied.createStatement()
        def stmt2 = proxied.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)
        def stmt3 = proxied.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT)

        then:
        stmt.connection.is(proxied)
        stmt2.connection.is(proxied)
        stmt3.connection.is(proxied)
    }

    def "proxies a databasemetadata correctly"() {
        given:
        def metadata = Mock(DatabaseMetaData)
        connection.getMetaData() >> metadata

        when:
        def proxiedMeta = proxied.getMetaData()

        then:
        proxiedMeta.connection.is(proxied)
    }

    def "proxies callables correctly"() {
        given:
        def statement1 = Mock(CallableStatement)
        def statement2 = Mock(CallableStatement)
        def statement3 = Mock(CallableStatement)
        connection.prepareCall(_) >> statement1
        connection.prepareCall(_, _, _) >> statement2
        connection.prepareCall(_, _, _, _) >> statement3

        when:
        def proxiedStatement1 = proxied.prepareCall("SQL")
        def proxiedStatement2 = proxied.prepareCall("SQL", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)
        def proxiedStatement3 = proxied.prepareCall("SQL", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT)

        then:
        proxiedStatement1.connection.is(proxied)
        proxiedStatement2.connection.is(proxied)
        proxiedStatement3.connection.is(proxied)
    }

    def "proxies prepared statements correctly"() {
        given:
        int[] intArray = new int[1]
        intArray[0] = 1
        def statement1 = Mock(PreparedStatement)
        def statement2 = Mock(PreparedStatement)
        def statement3 = Mock(PreparedStatement)
        def statement4 = Mock(PreparedStatement)
//        def statement5 = Mock(PreparedStatement)
        def statement6 = Mock(PreparedStatement)
        connection.prepareStatement(_) >> statement1
        connection.prepareStatement(_, _) >> statement2
        connection.prepareStatement(_, _, _, _) >> statement3
        connection.prepareStatement(_, 1) >> statement4
//        connection.prepareStatement(_, intArray) >> statement5
        connection.prepareStatement(_, ["col"].toArray(new String[0])) >> statement6

        when:
        def prepState1 = proxied.prepareStatement("SQL")
        def prepState2 = proxied.prepareStatement("SQL", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)
        def prepState3 = proxied.prepareStatement("SQL", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT)
        def prepState4 = proxied.prepareStatement("SQL", 1)
//        def prepState5 = proxied.prepareStatement("SQL", intArray as int[])
        def prepState6 = proxied.prepareStatement("SQL", ["col"].toArray(new String[0]))

        then:
        prepState1.connection.is(proxied)
        prepState2.connection.is(proxied)
        prepState3.connection.is(proxied)
        prepState4.connection.is(proxied)
//        prepState5.connection.is(proxied)
        prepState6.connection.is(proxied)
    }

    def "can log comitting a transaction"() {
        when:
        proxied.commit()

        then:
        1 * connection.commit()

        and:
        logOutput.first() == "Transaction committed"
    }

    private static ConnectionProxy proxyConnection(Connection connection) {
        new ConnectionProxy(connection)
    }
}
