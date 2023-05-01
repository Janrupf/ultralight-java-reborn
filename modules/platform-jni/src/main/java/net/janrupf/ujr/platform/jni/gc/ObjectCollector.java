package net.janrupf.ujr.platform.jni.gc;

import net.janrupf.ujr.platform.jni.ffi.NativeAccess;

import java.lang.ref.ReferenceQueue;

/**
 * This helper class allows native code to attach garbage collection callbacks to objects.
 */
public class ObjectCollector {
    // Queue which contains the objects to be collected
    private static final ThreadLocal<ReferenceQueue<Object>> QUEUE = ThreadLocal.withInitial(ReferenceQueue::new);

    /**
     * Attaches a native garbage collector to an object.
     *
     * @param object    the object to attach the collector to
     * @param collector the native collector to attach
     */
    @NativeAccess
    public static void attachCollector(Object object, long collector) {
        // Enqueue the reference
        // We don't need to store the resulting object, as its constructor adds it to the queue
        new NativeCollectedObjectReference(object, collector, QUEUE.get());
    }

    /**
     * Processes all objects that are ready to be collected.
     */
    public static void processRound() {
        // The inner workings of this method may seem a bit weird and complicated at first,
        // but should ensure a high performance. The idea is to collect multiple objects
        // in one native call, which is why we collect all objects in an array first.

        long[] toCollect = null;
        int used = 0;

        NativeCollectedObjectReference reference;
        while ((reference = (NativeCollectedObjectReference) QUEUE.get().poll()) != null) {
            // Found a reference to collect
            if (toCollect == null) {
                // Create an array in which we insert collectors
                toCollect = new long[8]; // 8 is an arbitrary number, we should check if this is a good value
            } else if (used == toCollect.length - 1) {
                // The array is full, so we need to create a new one with double the size
                long[] newToCollect = new long[toCollect.length * 2];
                System.arraycopy(toCollect, 0, newToCollect, 0, toCollect.length);
                toCollect = newToCollect;
            }

            // Insert element and advance data pointer
            toCollect[used] = reference.getCollector();
            used++;
        }

        if (toCollect != null) {
            // Run native collectors
            nativeCollect(toCollect);
        }
    }

    // This method requires the collectors array to contain at least one element
    // as it is terminated by a 0 value.
    private static native void nativeCollect(long[] collectors);
}
