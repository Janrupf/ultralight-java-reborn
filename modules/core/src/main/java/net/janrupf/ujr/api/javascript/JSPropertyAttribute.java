package net.janrupf.ujr.api.javascript;

/**
 * All possible JavaScript property attributes.
 */
public enum JSPropertyAttribute {
    /**
     * The property is read-only.
     */
    READ_ONLY,

    /**
     * The property is not enumerable.
     * <p>
     * This means that the property will not appear in {@code for-in} loops and other
     * means of enumerating properties.
     */
    DONT_ENUM,

    /**
     * The property can't be deleted.
     */
    DONT_DELETE
}
