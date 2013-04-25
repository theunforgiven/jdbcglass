package lt.nsg.jdbcglass.util

import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.AppenderBase
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.lang.ref.WeakReference

public class CapturingAppender extends AppenderBase<ILoggingEvent> {
    public static class Factory {
        private static final LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory()
        private static final List<WeakReference<CapturingAppender>> createdAppenders = []

        public static CapturingAppender create() {
            CapturingAppender capturer = new CapturingAppender()
            capturer.setContext(context)
            capturer.start()
            rootLogger.addAppender(capturer)
            createdAppenders.add(new WeakReference<CapturingAppender>(capturer))
            capturer
        }

        public static void cleanup() {
            createdAppenders*.get()
                            .findAll { it != null }
                            .each { CapturingAppender appender ->
                if (appender != null) {
                    rootLogger.detachAppender(appender)
                    appender.stop()
                }
            }
        }

        private static getRootLogger() {
            context.getLogger(Logger.ROOT_LOGGER_NAME)
        }
    }
    final List<ILoggingEvent> capturedEvents = []

    @Override
    protected void append(ILoggingEvent e) {
        capturedEvents << e
    }

    List<String> getMessages() {
        capturedEvents*.getFormattedMessage()
    }

    @Override
    void stop() {
        super.stop()
        clearMessages()
    }

    void clearMessages() {
        capturedEvents.clear()
    }

}
