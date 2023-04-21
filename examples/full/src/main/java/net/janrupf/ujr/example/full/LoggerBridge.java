package net.janrupf.ujr.example.full;

import net.janrupf.ujr.api.logger.UltralightLogLevel;
import net.janrupf.ujr.api.logger.UltralightLogger;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

/**
 * A simple logger bridge that delegates to a Log4j logger.
 * <p>
 * This shows you how to implement a custom logger for Ultralight, so you can delegate to your
 * favorite logging framework.
 */
public class LoggerBridge implements UltralightLogger {
    private final Logger delegate;

    public LoggerBridge(Logger delegate) {
        this.delegate = delegate;
    }

    @Override
    public void logMessage(UltralightLogLevel logLevel, String message) {
        Level translatedLevel = transateLogLevel(logLevel);
        delegate.log(translatedLevel, message);
    }

    /**
     * Helper function to translate from Ultralight's log levels to Log4j's log levels.
     *
     * @param logLevel the Ultralight log level to translate
     * @return the translated log level for Log4j
     */
    private Level transateLogLevel(UltralightLogLevel logLevel) {
        switch (logLevel) {
            // Map levels 1:1
            case ERROR:
                return Level.ERROR;
            case WARNING:
                return Level.WARN;
            case INFO:
                return Level.INFO;

            default:
                throw new RuntimeException("Unknown log level: " + logLevel);
        }
    }
}
