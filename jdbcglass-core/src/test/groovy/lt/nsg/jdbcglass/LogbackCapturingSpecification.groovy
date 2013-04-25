package lt.nsg.jdbcglass

import org.slf4j.Logger
import spock.lang.Specification

abstract class LogbackCapturingSpecification extends Specification {
    private final List<String> messages = []
    public Logger capturingLogger

    def setup() {
        capturingLogger = Mock(Logger)
        def capturedMessages = messages
        capturingLogger._(_) >> {
            capturedMessages << it.first().toString()
        }

        capturingLogger._(_, _) >> {
            String message = it.first()
            capturedMessages << message.replace("{}", it.last().toString())
        }
    }

    def cleanup() {
        messages.clear()
    }

    List<String> getLogOutput() {
        messages
    }

    void clearLog() {
        messages.clear()
    }
}


