package lt.nsg.jdbcglass.driver

import lt.nsg.jdbcglass.connection.LoggingConnectionProxy
import spock.lang.Specification

import java.sql.DriverManager

public class LoggingDriverSpecification extends Specification {

    @SuppressWarnings("UnnecessaryQualifiedReference")
    def "should have a service file containing an SPI definition for driver"() {
        given:
        ServiceLoader<java.sql.Driver> loadedDrivers = ServiceLoader.load(java.sql.Driver.class);
        expect:
        loadedDrivers.collect { it.class }.contains(lt.nsg.jdbcglass.driver.Driver)
    }

    def "should be able to use a connection string to enable logging"() {
        when:
        DriverManager.getDriver("jdbc:jdbcglass:h2:mem:mymemdb")

        then:
        noExceptionThrown()
    }

    def "should only accept urls that begin with 'jdbc:jdbcglass:' and ends with ;targetDriver=<driverName>"() {
        given:
        def driver = new Driver()

        expect:
        driver.acceptsURL(url) == isValidUrl

        where:
        url                                                     | isValidUrl
        "jdbc:jdbcglass:<this does not matter but is required>" | true
        'jdbc:h2:mem:test'                                      | false
        'garbage'                                               | false
        ''                                                      | false
    }

    def "should transform the URL and pass it on to the target driver class"() {
        given:
        def expectedProperties = new Properties()
        expectedProperties.put("ConnectionProperty", "A Value")
        def expectedUrl = "jdbc:jdbcglass:h2:mem:test"
        def driver = new Driver()

        when:
        def connection = driver.connect(expectedUrl, expectedProperties)

        then:
        noExceptionThrown()
        connection instanceof LoggingConnectionProxy
    }
}
