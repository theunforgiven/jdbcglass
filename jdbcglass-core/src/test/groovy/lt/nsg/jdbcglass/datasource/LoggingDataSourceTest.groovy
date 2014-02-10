package lt.nsg.jdbcglass.datasource

import lt.nsg.jdbcglass.connection.ConnectionProxyFactory
import lt.nsg.jdbcglass.datasource.LoggingDataSource
import spock.lang.Specification
import spock.lang.Subject

import javax.sql.DataSource
import java.sql.Connection

public class LoggingDataSourceTest extends Specification {
    private DataSource dataSource = Mock(DataSource)
    private ConnectionProxyFactory connectionProxyFactory = Mock(ConnectionProxyFactory)
    private Connection proxiedConnection = Mock(Connection)
    private Connection connection = Mock(Connection)

    @Subject
    def proxy = new LoggingDataSource(dataSource, connectionProxyFactory)

    def setup() {
        connectionProxyFactory.proxyConnection(connection) >> proxiedConnection
    }

    def "should proxy connections created by the target datasource with a username and password"() {
        given:
        def username = "username"
        def password = "password"
        dataSource.getConnection(username, password) >> connection

        when:
        def result = proxy.getConnection(username, password)

        then:
        result.is(proxiedConnection)
    }

    def "should proxy connections created by the target datasource"() {
        given:
        dataSource.getConnection() >> connection

        when:
        def result = proxy.getConnection()

        then:
        result.is(proxiedConnection)
    }
}
