package net.janrupf.ujr.api.javascript;

/**
 * Attributes for {@link JSClass}es.
 */
public enum JSClassAttribute {
    /**
     * Don't automatically assign a shared prototype to the class.
     * <p>
     * Managing prototypes manually can be done using {@link JSObject#setPrototype(JSValue)}.
     */
    NO_AUTOMATIC_PROTOTYPE,
}
