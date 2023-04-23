#pragma once

#include <Ultralight/Buffer.h>

#include "ujr/util/JniEnv.hpp"
#include "ujr/util/JniRef.hpp"

namespace ujr {
    /**
     * Helper class to create Java buffers wrapping native Ultralight buffers.
     */
    class BufferNative {
    public:
        explicit BufferNative() = delete;

        /**
         * Wraps a native Ultralight buffer into a Java buffer.
         *
         * @param env the JNI environment to use
         * @param buffer the native Ultralight buffer to wrap
         * @return the wrapped buffer
         */
        static JniLocalRef<jobject> as_java_buffer(const JniEnv &env, ultralight::RefPtr<ultralight::Buffer> buffer);
    };
} // namespace ujr
