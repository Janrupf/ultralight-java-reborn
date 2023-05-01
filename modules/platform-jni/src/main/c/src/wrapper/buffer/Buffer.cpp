#include "ujr/wrapper/buffer/Buffer.hpp"
#include "net_janrupf_ujr_platform_jni_wrapper_buffer_JNIUlDelegatedBuffer_native_access.hpp"

#include "ujr/util/JniClass.hpp"
#include "ujr/util/JniMethod.hpp"

namespace ujr {
    namespace {
        /**
         * Native access to the java.nio.ByteBuffer class.
         */
        class JniByteBuffer {
        public:
            explicit JniByteBuffer() = delete;

            static JniClass<"java/nio/ByteBuffer", jobject> CLAZZ;
            static JniInstanceMethod<decltype(CLAZZ), "hasArray", jboolean> HAS_ARRAY;
            static JniInstanceMethod<decltype(CLAZZ), "array", jbyteArray> ARRAY;
            static JniInstanceMethod<decltype(CLAZZ), "arrayOffset", jint> ARRAY_OFFSET;
            static JniInstanceMethod<decltype(CLAZZ), "position", jint> POSITION;
            static JniInstanceMethod<decltype(CLAZZ), "limit", jint> LIMIT;
            static JniInstanceMethod<decltype(CLAZZ), "get", decltype(CLAZZ), jbyteArray> GET;
        };

        JniClass<"java/nio/ByteBuffer", jobject> JniByteBuffer::CLAZZ {};
        JniInstanceMethod<decltype(JniByteBuffer::CLAZZ), "hasArray", jboolean> JniByteBuffer::HAS_ARRAY {
            JniByteBuffer::CLAZZ
        };
        JniInstanceMethod<decltype(JniByteBuffer::CLAZZ), "array", jbyteArray> JniByteBuffer::ARRAY {
            JniByteBuffer::CLAZZ
        };
        JniInstanceMethod<decltype(JniByteBuffer::CLAZZ), "arrayOffset", jint> JniByteBuffer::ARRAY_OFFSET {
            JniByteBuffer::CLAZZ
        };
        JniInstanceMethod<decltype(JniByteBuffer::CLAZZ), "position", jint> JniByteBuffer::POSITION {
            JniByteBuffer::CLAZZ
        };
        JniInstanceMethod<decltype(JniByteBuffer::CLAZZ), "limit", jint> JniByteBuffer::LIMIT { JniByteBuffer::CLAZZ };
        JniInstanceMethod<decltype(JniByteBuffer::CLAZZ), "get", decltype(JniByteBuffer::CLAZZ), jbyteArray>
            JniByteBuffer::GET { JniByteBuffer::CLAZZ };

        /**
         * Helper class to hold a delegated buffer. Used to interact with C-style callbacks.
         */
        class DelegatedBufferHolder {
        private:
            JniGlobalRef<jobject> delegated_buffer;

        public:
            /**
             * Constructs a new delegated buffer holder.
             *
             * @param delegated_buffer the delegated buffer to hold
             */
            explicit DelegatedBufferHolder(JniGlobalRef<jobject> delegated_buffer)
                : delegated_buffer(std::move(delegated_buffer)) {}

            /**
             * Calls the release method on the delegated buffer.
             */
            void release() {
                using native_access::JNIUlDelegatedBuffer;

                auto env = JniEnv::from_thread();
                JNIUlDelegatedBuffer::RELEASE.invoke(env, delegated_buffer);
            }

            DelegatedBufferHolder(const DelegatedBufferHolder &) = delete;
            DelegatedBufferHolder(DelegatedBufferHolder &&) = delete;
            DelegatedBufferHolder &operator=(const DelegatedBufferHolder &) = delete;
            DelegatedBufferHolder &operator=(DelegatedBufferHolder &&) = delete;

            /**
             * Releases the delegated buffer.
             *
             * @param buffer the delegated buffer holder
             */
            static void delete_callback(void *buffer, void *) {
                auto *holder = static_cast<DelegatedBufferHolder *>(buffer);

                // Make sure to _always_ release the holder
                // This is ugly, but not worth the effort to make it nicer using RAII
                try {
                    holder->release();
                    delete holder;
                } catch (...) {
                    delete holder;
                    throw;
                }
            }
        };
    } // namespace

    ultralight::RefPtr<ultralight::Buffer>
    Buffer::wrap_delegated_buffer(const JniEnv &env, const JniStrongRef<jobject> &delegated_buffer) {
        using native_access::JNIUlDelegatedBuffer;

        auto byte_buffer = JNIUlDelegatedBuffer::GET_BYTE_BUFFER.invoke(env, delegated_buffer);

        // Get the current position and limit of the buffer
        auto buffer_position = JniByteBuffer::POSITION.invoke(env, byte_buffer);
        auto buffer_limit = JniByteBuffer::LIMIT.invoke(env, byte_buffer);
        auto buffer_length = buffer_limit - buffer_position;

        // First attempt: Get the buffer data directly
        auto direct_data = static_cast<unsigned char *>(env->GetDirectBufferAddress(byte_buffer));

        if (direct_data != nullptr) {
            // Adjust the data pointer, so it points to the current position
            auto real_data = direct_data + buffer_position;

            if (reinterpret_cast<size_t>(direct_data) % 16 != 0) {
                // Manual alignment is required, we simply copy the data
                auto ultralight_buffer = ultralight::Buffer::CreateFromCopy(real_data, buffer_length);

                // Release the java buffer
                JNIUlDelegatedBuffer::RELEASE.invoke(env, delegated_buffer);

                return ultralight_buffer;
            } else {
                // We need to keep the buffer referenced, so we create a holder with a global reference
                // This will also later release the java buffer when the Ultralight buffer is released
                auto *holder = new DelegatedBufferHolder(delegated_buffer.clone_as_global(env));

                // Wrap the buffer with a release callback
                return ultralight::Buffer::Create(
                    real_data,
                    buffer_length,
                    holder,
                    DelegatedBufferHolder::delete_callback
                );
            }
        }

        // Second attempt: Copy the buffer data, we are dealing with an indirect buffer using arrays
        auto byte_buffer_has_array = JniByteBuffer::HAS_ARRAY.invoke(env, byte_buffer);

        auto byte_buffer_array = JniLocalRef<jbyteArray>::null(env);
        size_t byte_buffer_array_offset;

        if (byte_buffer_has_array) {
            // Has array, we can get the array and offset directly
            byte_buffer_array = JniByteBuffer::ARRAY.invoke(env, byte_buffer);
            byte_buffer_array_offset = JniByteBuffer::ARRAY_OFFSET.invoke(env, byte_buffer);
        } else {
            // No array, create one to copy the data to
            byte_buffer_array = JniLocalRef<jbyteArray>::wrap(env, env->NewByteArray(buffer_length));
            byte_buffer_array_offset = 0;

            // Copy the data
            JniByteBuffer::GET.invoke(env, byte_buffer, byte_buffer_array);
        }

        auto *elements = static_cast<unsigned char *>(
            env->GetPrimitiveArrayCritical(reinterpret_cast<jarray>(byte_buffer_array.get()), nullptr)
        );
        if (!elements) {
            // OOM
            JniExceptionCheck::throw_if_pending(env); // Should always throw
            throw std::bad_alloc(); // Just in case the JVM didn't throw for whatever reason
        }

        // Create the copy
        auto ultralight_buffer
            = ultralight::Buffer::CreateFromCopy(elements + byte_buffer_array_offset + buffer_position, buffer_length);

        // Release the java array and buffer
        env->ReleasePrimitiveArrayCritical(reinterpret_cast<jarray>(byte_buffer_array.get()), elements, JNI_ABORT);
        JNIUlDelegatedBuffer::RELEASE.invoke(env, delegated_buffer);

        return ultralight_buffer;
    }
} // namespace ujr
