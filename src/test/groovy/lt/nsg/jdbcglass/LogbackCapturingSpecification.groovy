package lt.nsg.jdbcglass

import lt.nsg.jdbcglass.util.CapturingAppender
import spock.lang.Specification

abstract class LogbackCapturingSpecification extends Specification{
    private static CapturingAppender capturer

    def setupSpec() {
        capturer = CapturingAppender.Factory.create()
    }

    def cleanupSpec() {
        CapturingAppender.Factory.cleanup()
    }

    def cleanup() {
        capturer.clearMessages()
    }

    List<String> getLogOutput() {
        capturer.messages
    }

    void clearLog() {
        capturer.clearMessages()
    }
}


