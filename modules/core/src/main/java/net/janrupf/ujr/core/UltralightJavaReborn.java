package net.janrupf.ujr.core;

import net.janrupf.ujr.core.platform.PlatformEnvironment;

/**
 * Main entry point for the Ultralight Java Reborn library.
 */
public class UltralightJavaReborn implements AutoCloseable {
    private static final ThreadLocal<UltralightJavaReborn> THREAD_INSTANCE = new ThreadLocal<>();

    private final PlatformEnvironment platformEnvironment;

    private Thread boundThread;

    /**
     * Retrieves the active Ultralight Java Reborn instance for the current thread.
     * <p>
     * This may only be called after {@link #activate()} has been called on the current thread.
     *
     * @return the active Ultralight Java Reborn instance
     * @throws IllegalStateException if no Ultralight Java Reborn instance is active on this thread
     */
    public static UltralightJavaReborn getActiveInstance() {
        UltralightJavaReborn instance = THREAD_INSTANCE.get();

        if (instance == null) {
            throw new IllegalStateException("No Ultralight Java Reborn instance is active on this thread");
        }

        return instance;
    }

    /**
     * Creates a new Ultralight Java Reborn instance.
     * <p>
     * While Java technically allows you to call this multiple times, doing so is not recommended at
     * the moment. This may change in the future.
     *
     * @param platformEnvironment the platform environment to use
     */
    public UltralightJavaReborn(PlatformEnvironment platformEnvironment) {
        this.platformEnvironment = platformEnvironment;
    }

    /**
     * Performs cleanup of the library.
     * <p>
     * This should be called after you are done using the library.
     */
    public void cleanup() {
        close();
    }

    /**
     * Activates the current thread as the Ultralight Java Reborn thread.
     * <p>
     * All Ultralight Java Reborn objects must be created on the Ultralight Java Reborn thread and used
     * there. This method should be called before creating any Ultralight Java Reborn objects.
     *
     * @throws IllegalStateException if another Ultralight Java Reborn instance is already active on this thread or
     *                               if this Ultralight Java Reborn instance is already bound to a thread
     */
    public void activate() {
        if (THREAD_INSTANCE.get() != null) {
            throw new IllegalStateException("Another Ultralight Java Reborn instance is already active on this thread");
        } else if (this.boundThread != null) {
            throw new IllegalStateException("This Ultralight Java Reborn instance is already bound to a thread");
        }

        this.boundThread = Thread.currentThread();

        THREAD_INSTANCE.set(this);
    }

    /**
     * Determines whether the current thread is the Ultralight Java Reborn thread belonging to this instance.
     *
     * @return true if the current thread is the Ultralight Java Reborn thread belonging to this instance,
     * false otherwise
     */
    public boolean isOnCorrectThread() {
        return this.boundThread != null && this.boundThread.equals(Thread.currentThread());
    }

    /**
     * Validates that the current thread is the Ultralight Java Reborn thread belonging to this instance.
     *
     * @throws IllegalStateException if the current thread is not the Ultralight Java Reborn thread belonging to this
     *                               instance
     */
    public void validateThread() {
        if (!isOnCorrectThread()) {
            throw new IllegalStateException("This method must be called on the Ultralight Java Reborn thread");
        }
    }

    /**
     * Retrieves the platform environment backing this Ultralight Java Reborn instance.
     *
     * @return the platform environment backing this instance
     */
    public PlatformEnvironment getPlatformEnvironment() {
        return platformEnvironment;
    }

    @Override
    public void close() {
        this.platformEnvironment.cleanup();
    }
}
