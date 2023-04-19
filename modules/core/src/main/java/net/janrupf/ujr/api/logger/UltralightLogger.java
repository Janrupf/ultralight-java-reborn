package net.janrupf.ujr.api.logger;

/**
 * Logger interface.
 * <p>
 * This can be used to log debug messages to the console or to a log file.
 * <p>
 * This is intended to be implemented by users and defined before creating the Renderer.
 *
 * @see net.janrupf.ujr.api.UltralightPlatform#setLogger(UltralightLogger)
 */
public interface UltralightLogger {
    /**
     * Called when the library wants to print a message to the log.
     *
     * @param logLevel the level the message was logged at
     * @param message  the message to log
     */
    void logMessage(UltralightLogLevel logLevel, String message);
}
