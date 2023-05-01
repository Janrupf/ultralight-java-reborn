package net.janrupf.ujr.platform.jni.gc;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * Phantom reference to a Java object which has a native collector attached.
 */
public class NativeCollectedObjectReference extends PhantomReference<Object> {
    private final long collector;

    public NativeCollectedObjectReference(Object referent, long collector, ReferenceQueue<? super Object> q) {
        super(referent, q);

        this.collector = collector;
    }

    /**
     * Retrieves the native collector that is attached to the object.
     *
     * @return the native collector attached to the object
     */
    public long getCollector() {
        return collector;
    }
}
