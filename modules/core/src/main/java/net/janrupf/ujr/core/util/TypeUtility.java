package net.janrupf.ujr.core.util;

/**
 * A collection of utility methods for working with types.
 * This is internal API and should not be accessed externally!
 */
public final class TypeUtility {
    private TypeUtility() {
        throw new UnsupportedOperationException();
    }

    /**
     * Casts an object to a specific type without any type safety checks.
     *
     * @param object the object to cast
     * @return the casted object
     * @param <T> the type to cast to
     */
    @SuppressWarnings("unchecked")
    public static <T> T uncheckedCast(Object object) {
        return (T) object;
    }
}
