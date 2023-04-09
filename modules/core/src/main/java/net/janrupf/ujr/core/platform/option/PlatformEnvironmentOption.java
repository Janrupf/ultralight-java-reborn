package net.janrupf.ujr.core.platform.option;

import java.util.Objects;

/**
 * A simple (mostly internal) holder for options passed to platform environments.
 * <p></p>
 * This assists to aid debugging and not restricting the options which platform providers can
 * accept.
 * <p>
 * Classes are used as the registration keys and uniquely identify options in the map of available
 * options.
 *
 * @param <T> the type this platform environment provider is registered as
 */
public class PlatformEnvironmentOption<T> {
    private final Class<T> registrationKey;
    private final T value;

    private boolean wasUsed;

    /**
     * Creates a new option for a specific class.
     * <p>
     * The class has to be provided explicitly, since it may be any super class
     * of the supplied value. Please note that in the end options are matched
     * based on the class passed as the key here and the library performs no
     * attempts to detect polymorphic usage. The class is matched 1-to-1
     * in a {@link java.util.Map}, you have been warned!
     *
     * @param registrationKey the registration key the options is matched based on
     * @param value           the actual value of the option
     */
    public <V extends T> PlatformEnvironmentOption(Class<T> registrationKey, V value) {
        Objects.requireNonNull(registrationKey, "registrationKey must not be null");
        Objects.requireNonNull(value, "value must not be null");

        this.registrationKey = registrationKey;
        this.value = value;
    }

    /**
     * Marks this option as used.
     * <p>
     * You usually should not call this method manually, the library will take care of it.
     */
    public void markUsed() {
        this.wasUsed = true;
    }

    /**
     * Determines whether this option was used by the platform environment.
     *
     * @return true if the option was used, false otherwise
     */
    public boolean wasUsed() {
        return wasUsed;
    }

    /**
     * Retrieves the registration key of this option.
     *
     * @return the registration key of this option
     */
    public Class<T> getRegistrationKey() {
        return registrationKey;
    }

    /**
     * Retrieves the value of this option.
     *
     * @return the value of this option
     */
    public T getValue() {
        return value;
    }
}
