package net.janrupf.ujr.core.platform.abstraction;

public interface UlSession {
    boolean isPersistent();

    String name();

    long id();

    String diskPath();
}
