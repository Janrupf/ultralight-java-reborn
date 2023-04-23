#pragma once

#include <jni.h>
#include <Ultralight/Buffer.h>
#include <Ultralight/RefPtr.h>

#include "ujr/util/JniEnv.hpp"
#include "ujr/util/JniRef.hpp"

namespace ujr {
    /**
     * Helper class to deal with java buffers.
     */
    class Buffer {
    public:
        explicit Buffer() = delete;

        /**
         * Wraps a java delegated buffer into a ultralight buffer.
         *
         * The delegated buffer will be released automatically either immediately or when the
         * the ultralight buffer is released.
         *
         * Buffers allocated using this method will always be 16 byte aligned.
         *
         * @param env the JNI environment to use
         * @param delegated_buffer the delegated buffer to wrap
         * @return the wrapped buffer
         */
        static ultralight::RefPtr<ultralight::Buffer>
        wrap_delegated_buffer(const JniEnv &env, const JniStrongRef<jobject> &delegated_buffer);
    };
} // namespace ujr
