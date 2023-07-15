package net.janrupf.ujr.api.javascript;

/**
 * All possible JavaScript types.
 */
public enum JSType {
    /**
     * The unique value {@code undefined}.
     */
    UNDEFINED,

    /**
     * The unique value {@code null}.
     */
    NULL,

    /**
     * A primitive boolean value, one of {@code true} or {@code false}.
     */
    BOOLEAN,

    /**
     * A primitive number value.
     */
    NUMBER,

    /**
     * A primitive string value.
     */
    STRING,

    /**
     * An object value (meaning that this is a reference to an object).
     */
    OBJECT,

    /**
     * A primitive symbol value.
     */
    SYMBOL
}
