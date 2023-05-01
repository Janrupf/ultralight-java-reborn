#pragma once

#include "ujr/util/JniEnv.hpp"
#include "ujr/util/JniRef.hpp"

namespace ujr {
    /**
     * Collector attached to Java objects in order to allow destruction of C++ objects.
     */
    class NativeCollector {
    public:
        explicit NativeCollector();
        virtual ~NativeCollector();

        NativeCollector(const NativeCollector &other) = delete;
        NativeCollector(NativeCollector &&other) = delete;

        NativeCollector &operator=(const NativeCollector &other) = delete;
        NativeCollector &operator=(NativeCollector &&other) = delete;

        /**
         * Garbage collects the object and free's the underlying resources.
         */
        virtual void collect() = 0;
    };

    /**
     * Helper to deal with garbage collection.
     */
    class GCSupport {
    public:
        explicit GCSupport() = delete;

        /**
         * Attaches a native collector to a Java object.
         *
         * @param env the JNI environment to use
         * @param obj the object to attach the collector to
         * @param collector the collector to attach, ownership is taken
         */
        static void
        attach_collector(const JniEnv &env, const JniStrongRef<jobject> &obj, NativeCollector *collector);
    };
} // namespace ujr
