package net.janrupf.ujr.api.javascript;

/**
 * Exception thrown when a Javascript error occurs and propagates back to Java and
 * the error is a JavaScript value.
 */
public class JavaScriptValueException extends JavaScriptException {
    private final JSValue value;

    public JavaScriptValueException(JSValue value, String message) {
        super(message);
        this.value = value;
    }

    /**
     * Retrieves the JavaScript value that was thrown.
     *
     * @return the JavaScript value
     */
    public JSValue getValue() {
        return value;
    }
}
