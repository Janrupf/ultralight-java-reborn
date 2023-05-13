package net.janrupf.ujr.api;

// TODO: fix doc comment references

import net.janrupf.ujr.core.platform.abstraction.UlSession;

/**
 * A Session stores local data such as cookies, local storage, and application cache for
 * one or more Views.
 *
 * @see Renderer::CreateSession
 */
public class UltralightSession {
    private final UlSession session;

    /* package */ UltralightSession(UlSession session) {
        this.session = session;
    }

    /**
     * Determines whether this session is written to disk.
     *
     * @return true if this session is written to disk, false otherwise
     */
    public boolean isPersistent() {
        return session.isPersistent();
    }

    /**
     * Retrieves a unique name identifying this session.
     *
     * @return the name of this session
     */
    public String name() {
        return session.name();
    }

    /**
     * Retrieves a unique numeric ID identifying this session.
     *
     * @return the ID of this session
     */
    public long id() {
        return session.id();
    }

    /**
     * The disk path of this session (only valid for persistent sessions).
     *
     * @return the disk path of this session, or null if this session is not persistent
     */
    public String diskPath() {
        return session.diskPath();
    }

    // Only for use by platform implementations
    public UlSession getImplementation() {
        return session;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof UltralightSession)) {
            return false;
        }

        return session.equals(((UltralightSession) obj).session);
    }

    @Override
    public int hashCode() {
        return session.hashCode();
    }
}
