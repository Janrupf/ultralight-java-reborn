package net.janrupf.ujr.core.platform.option;

import net.janrupf.ujr.core.util.TypeUtility;

import java.util.HashMap;
import java.util.Map;

/**
 * Container of platform environment options.
 * <p>
 * This container keeps track of the options passed to a platform environment and allows to
 * retrieve them later. It also allows to freeze the container, which prevents further options
 * from being added. This is used to prevent the platform environments from modifying the options.
 */
public class PlatformEnvironmentOptionContainer {
    private final Map<Class<?>, PlatformEnvironmentOption<?>> options;
    private boolean frozen;

    /**
     * Creates a new, empty option container.
     */
    public PlatformEnvironmentOptionContainer() {
        this.options = new HashMap<>();
        this.frozen = false;
    }

    /**
     * Adds an option to the container.
     * <p>
     * If an option with the same registration key is already present, it will be overwritten.
     *
     * @param option the option to add
     * @throws IllegalStateException if the container is already frozen
     */
    public void addOption(PlatformEnvironmentOption<?> option) {
        if (frozen) {
            throw new IllegalStateException("Cannot add options to a frozen container");
        }

        options.put(option.getRegistrationKey(), option);
    }

    /**
     * Freezes the container and prevents further options from being added.
     */
    public void freeze() {
        // Prepare all options before freezing the container
        for (PlatformEnvironmentOption<?> option : options.values()) {
            Object value = option.getValue();
            if (value instanceof PlatformEnvironmentOption.PreparationHook) {
                ((PlatformEnvironmentOption.PreparationHook) value).prepare();
            }
        }

        frozen = true;
    }

    /**
     * Retrieves whether the container is frozen already.
     *
     * @return true if the container is frozen, false otherwise
     */
    public boolean isFrozen() {
        return frozen;
    }

    /**
     * Determines whether the container contains an option with the given registration key.
     *
     * @param registrationKey the key used to register the option
     * @return true if the container contains an option with the given key, false otherwise
     */
    public boolean has(Class<?> registrationKey) {
        return options.containsKey(registrationKey);
    }

    /**
     * Retrieves an option from the container and marks it as used.
     * If the option does not exist, an exception is thrown.
     *
     * @param registrationKey the key used to register the option
     * @param <T>             the type of the option
     * @return the registered option
     * @throws IllegalStateException if the option is not present
     */
    public <T> T require(Class<T> registrationKey) {
        T option = use(registrationKey);

        if (option == null) {
            throw new IllegalStateException("Required option " + registrationKey.getName() + " is not present");
        }

        return option;
    }

    /**
     * Retrieves an option from the container and marks it as used, if it exists.
     *
     * @param registrationKey the key used to register the option
     * @param <T>             the type of the option
     * @return the registered option, or null, if no option with the given key was registered
     */
    public <T> T use(Class<T> registrationKey) {
        PlatformEnvironmentOption<?> option = options.get(registrationKey);
        if (option == null) {
            return null;
        }

        option.markUsed();

        // The contract of PlatformEnvironmentOption is that the value is always of
        // a compatible type.
        return TypeUtility.uncheckedCast(option.getValue());
    }
}
