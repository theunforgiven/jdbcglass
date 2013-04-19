package lt.nsg.jdbcglass

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class LogbackCapturingSpecificationTest extends LogbackCapturingSpecification{
    private final static Logger log = LoggerFactory.getLogger(LogbackCapturingSpecificationTest)

    def "can capture logback output"() {
        given:
        def message = "a message"

        when:
        log.info(message)

        then:
        logOutput.first() == message
    }
}
