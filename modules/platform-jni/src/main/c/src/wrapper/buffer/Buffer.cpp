#include "ujr/wrapper/buffer/Buffer.hpp"
#include "net_janrupf_ujr_platform_jni_wrapper_buffer_JNIUlDelegatedBuffer_native_access.hpp"

#include <cstdlib>
#include <cstring>
#include <memory>

#include "ujr/util/JniClass.hpp"
#include "ujr/util/JniMethod.hpp"

#if _MSC_VER
#define ALIGNED_MALLOC(size, alignment) _aligned_malloc((size), (alignment))
#define ALIGNED_FREE(ptr) _aligned_free((ptr))
#else
#define ALIGNED_MALLOC(size, alignment) ::std::aligned_alloc((alignment), (size))
#define ALIGNED_FREE(ptr) ::std::free((ptr))
#endif

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
            static JniInstanceMethod<decltype(CLAZZ), "position", jint, jint> SET_POSITION;
            static JniInstanceMethod<decltype(CLAZZ), "limit", jint> LIMIT;
            static JniInstanceMethod<decltype(CLAZZ), "get", decltype(CLAZZ), jbyteArray> GET;
            static JniInstanceMethod<decltype(CLAZZ), "put", decltype(CLAZZ), jbyteArray> PUT;
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
        JniInstanceMethod<decltype(JniByteBuffer::CLAZZ), "position", jint, jint> JniByteBuffer::SET_POSITION {
            JniByteBuffer::CLAZZ
        };
        JniInstanceMethod<decltype(JniByteBuffer::CLAZZ), "limit", jint> JniByteBuffer::LIMIT { JniByteBuffer::CLAZZ };
        JniInstanceMethod<decltype(JniByteBuffer::CLAZZ), "get", decltype(JniByteBuffer::CLAZZ), jbyteArray>
            JniByteBuffer::GET { JniByteBuffer::CLAZZ };
        JniInstanceMethod<decltype(JniByteBuffer::CLAZZ), "put", decltype(JniByteBuffer::CLAZZ), jbyteArray>
            JniByteBuffer::PUT { JniByteBuffer::CLAZZ };

        enum class DelegatedBufferArrayType {
            INVALID, // The buffer is not backed by a java array
            DIRECT_NO_COPY, // The array elements are directly backed by the java array content
            DIRECT_COPY, // The array elements are a copy, but can be committed back to the java array
            INDIRECT_COPY // The array elements are an entirely separate copy
        };

        /**
         * Helper class to hold a delegated buffer. Used to interact with C-style callbacks.
         */
        class DelegatedBufferHolder {
        private:
            JniGlobalRef<jobject> delegated_buffer;

            jint region_length;

            // For buffers backed by a direct region with copy-back semantics
            void *direct_region;
            void *copy_region;

            // For buffers backed by arrays
            JniGlobalRef<jobject> byte_buffer;
            JniGlobalRef<jbyteArray> backing_array;
            jbyte *elements;
            DelegatedBufferArrayType array_type;
            jint array_offset;
            jint position;

        public:
            /**
             * Constructs a new delegated buffer holder which requires not further
             * attention when releasing.
             *
             * @param delegated_buffer the delegated buffer to hold
             * @param backing_buffer_release an optional release callback for the backing buffer
             */
            explicit DelegatedBufferHolder(JniGlobalRef<jobject> delegated_buffer)
                : delegated_buffer(std::move(delegated_buffer))
                , region_length(0)
                , direct_region(nullptr)
                , copy_region(nullptr)
                , byte_buffer(JniGlobalRef<jobject>::null())
                , backing_array(JniGlobalRef<jbyteArray>::null())
                , elements(nullptr)
                , array_type(DelegatedBufferArrayType::INVALID)
                , array_offset(0)
                , position(0) {}

            /**
             * Constructs a new delegated buffer holder which requires copying data back
             * through a memcpy when releasing.
             *
             * @param delegated_buffer the delegated buffer to hold
             * @param direct_region the region behind the direct buffer
             * @param copy_region the region behind the copy buffer
             * @param region_length the length of the copy region
             */
            explicit DelegatedBufferHolder(
                JniGlobalRef<jobject> delegated_buffer, void *direct_region, void *copy_region, jint region_length
            )
                : delegated_buffer(std::move(delegated_buffer))
                , region_length(region_length)
                , direct_region(direct_region)
                , copy_region(copy_region)
                , byte_buffer(JniGlobalRef<jobject>::null())
                , backing_array(JniGlobalRef<jbyteArray>::null())
                , elements(nullptr)
                , array_type(DelegatedBufferArrayType::INVALID)
                , array_offset(0)
                , position(0) {}

            /**
             * Constructs a new delegated buffer holder which requires copying data back
             * through an array when releasing.
             *
             * @param delegated_buffer the delegated buffer to hold
             * @param byte_buffer the java.nio.ByteBuffer instance backing the buffer
             * @param backing_array the array backing the buffer
             * @param elements the elements of the backing array
             * @param array_type the type of the backing array and its elements
             * @param region_length the length of the region of the backing array used by the buffer
             * @param array_offset the start offset of the data of the buffer within the array
             * @param position the start offset of the data within the array
             */
            explicit DelegatedBufferHolder(
                JniGlobalRef<jobject> delegated_buffer,
                JniGlobalRef<jobject> byte_buffer,
                JniGlobalRef<jbyteArray> backing_array,
                jbyte *elements,
                DelegatedBufferArrayType array_type,
                jint region_length,
                jint array_offset,
                jint position
            )
                : delegated_buffer(std::move(delegated_buffer))
                , region_length(region_length)
                , direct_region(nullptr)
                , copy_region(nullptr)
                , byte_buffer(std::move(byte_buffer))
                , backing_array(std::move(backing_array))
                , elements(elements)
                , array_type(array_type)
                , array_offset(array_offset)
                , position(position) {}

            /**
             * Calls the release method on the delegated buffer.
             */
            void release() {
                using native_access::JNIUlDelegatedBuffer;

                auto env = JniEnv::from_thread();

                if (copy_region) {
                    // Copy back the data from the copy region to the direct region
                    std::memcpy(direct_region, copy_region, region_length);
                    ALIGNED_FREE(copy_region);

                    copy_region = nullptr;
                }

                if (!backing_array.is_null(env)) {
                    if (array_type == DelegatedBufferArrayType::DIRECT_NO_COPY
                        || (array_type == DelegatedBufferArrayType::DIRECT_COPY && array_offset == 0 && position == 0
                        )) {
                        // Case 1: The array is no copy at all, changes are commit already anyway, and we don't need to
                        // worry about partially updating the array.
                        //
                        // Case 2: The array is a copy, but the entire buffer is backed by the array, so we can simply
                        // commit the changes back to the array.
                        //
                        // For those 2 cases, we simply release the elements and are done.
                        env->ReleaseByteArrayElements(backing_array, elements, 0);
                        elements = nullptr;
                    } else if (array_type == DelegatedBufferArrayType::DIRECT_COPY) {
                        // Slow path, only parts of the buffer are backed by the array,
                        // we should not commit changes to the array as else we might overwrite changes made by Java.

                        // Save the current buffer position and reset it to what we saved
                        auto new_buffer_position = JniByteBuffer::POSITION.invoke(env, byte_buffer);
                        JniByteBuffer::SET_POSITION.invoke(env, byte_buffer, position);

                        // Create a temporary array to copy the partial data to
                        auto temp_array = JniLocalRef<jbyteArray>::wrap(env, env->NewByteArray(region_length));

                        // Copy the partial data to the temporary array
                        env->SetByteArrayRegion(temp_array, 0, region_length, elements);

                        // Put the temporary array into the byte buffer
                        JniByteBuffer::PUT.invoke(env, byte_buffer, temp_array);
                        JniByteBuffer::SET_POSITION.invoke(env, byte_buffer, new_buffer_position); // Revert changes

                        // Release the backing array elements without any copy back
                        env->ReleaseByteArrayElements(backing_array, elements, JNI_ABORT);
                        elements = nullptr;
                    } else {
                        // Slow path, the array is entirely separate from the buffer.

                        // Make sure the separate array is up-to-date with native writes
                        env->ReleaseByteArrayElements(backing_array, elements, JNI_COMMIT);
                        elements = nullptr;

                        // Save the current buffer position and reset it to what we saved
                        auto new_buffer_position = JniByteBuffer::POSITION.invoke(env, byte_buffer);
                        JniByteBuffer::SET_POSITION.invoke(env, byte_buffer, position);

                        // Copy the separate array into the buffer
                        JniByteBuffer::PUT.invoke(env, byte_buffer, backing_array.get());

                        JniByteBuffer::SET_POSITION.invoke(env, byte_buffer, new_buffer_position); // Revert changes
                    }
                }

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
                    // If something goes wrong, lets at least free the memory
                    if (holder->copy_region) {
                        ALIGNED_FREE(holder->copy_region);
                    }

                    if (holder->elements) {
                        auto env = JniEnv::from_thread();
                        env->ReleaseByteArrayElements(holder->backing_array, holder->elements, JNI_ABORT);
                    }

                    delete holder;
                    throw;
                }
            }
        };
    } // namespace

    ultralight::RefPtr<ultralight::Buffer> Buffer::wrap_delegated_buffer(
        const JniEnv &env, const JniStrongRef<jobject> &delegated_buffer, bool support_writeback
    ) {
        using native_access::JNIUlDelegatedBuffer;

        auto byte_buffer = JNIUlDelegatedBuffer::GET_BYTE_BUFFER.invoke(env, delegated_buffer);

        // Get the current position and limit of the buffer
        auto buffer_position = JniByteBuffer::POSITION.invoke(env, byte_buffer);
        auto buffer_limit = JniByteBuffer::LIMIT.invoke(env, byte_buffer);
        auto buffer_length = buffer_limit - buffer_position;

        // First attempt: Get the buffer data directly
        auto direct_data = static_cast<unsigned char *>(env->GetDirectBufferAddress(byte_buffer));

        ultralight::RefPtr<ultralight::Buffer> ultralight_buffer = nullptr;

        if (direct_data != nullptr) {
            // Adjust the data pointer, so it points to the current position
            auto real_data = direct_data + buffer_position;

            if (reinterpret_cast<size_t>(real_data) % 16 == 0) {
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
            } else {
                // Manual alignment is required
                if (!support_writeback) {
                    // If no writeback support is required, a one-off copy is enough to get the data
                    ultralight_buffer = ultralight::Buffer::CreateFromCopy(real_data, buffer_length);

                    // Release the java buffer
                    JNIUlDelegatedBuffer::RELEASE.invoke(env, delegated_buffer);

                    return ultralight_buffer;
                }

                // Copy the data to an aligned buffer
                void *copy_region = ALIGNED_MALLOC(buffer_length, 16);
                std::memcpy(copy_region, real_data, buffer_length);

                auto *holder = new DelegatedBufferHolder(
                    delegated_buffer.clone_as_global(env),
                    real_data,
                    copy_region,
                    buffer_length
                );

                // Wrap the buffer with a release callback
                return ultralight::Buffer::Create(
                    copy_region,
                    buffer_length,
                    holder,
                    DelegatedBufferHolder::delete_callback
                );
            }
        } else {
            // We can't directly access the buffer

            // Second attempt: Copy the buffer data, we are dealing with an indirect buffer using arrays
            auto byte_buffer_has_array = JniByteBuffer::HAS_ARRAY.invoke(env, byte_buffer);

            auto byte_buffer_array = JniLocalRef<jbyteArray>::null(env);
            jint byte_buffer_array_offset;

            if (byte_buffer_has_array) {
                // Has array, we can get the array and offset directly
                byte_buffer_array = JniByteBuffer::ARRAY.invoke(env, byte_buffer);
                byte_buffer_array_offset = JniByteBuffer::ARRAY_OFFSET.invoke(env, byte_buffer) + buffer_position;
            } else {
                // No array, create one to copy the data to
                byte_buffer_array = JniLocalRef<jbyteArray>::wrap(env, env->NewByteArray(buffer_length));
                byte_buffer_array_offset = 0;

                // Copy the data
                JniByteBuffer::GET.invoke(env, byte_buffer, byte_buffer_array);
                JniByteBuffer::SET_POSITION.invoke(env, byte_buffer, buffer_position); // Reset position
            }

            jbyte *elements;
            jboolean elements_is_copy = false;

            if (!support_writeback) {
                // If no writeback is desired, we can safely use a critical array
                // as we don't make any JNI calls for a read only buffer before we copy the data
                elements = static_cast<jbyte *>(
                    env->GetPrimitiveArrayCritical(reinterpret_cast<jarray>(byte_buffer_array.get()), nullptr)
                );
            } else {
                // For writeback purpose, we obtain a normal pointer
                elements = static_cast<jbyte *>(env->GetByteArrayElements(byte_buffer_array.get(), &elements_is_copy));
            }

            if (!elements) {
                // OOM
                JniExceptionCheck::throw_if_pending(env); // Should always throw
                throw std::bad_alloc(); // Just in case the JVM didn't throw for whatever reason
            }

            // Create the copy
            if (!support_writeback) {
                // If no writeback support is required, a one-off copy is enough to get the data
                ultralight_buffer
                    = ultralight::Buffer::CreateFromCopy(elements + byte_buffer_array_offset, buffer_length);

                // Release the java array and the java buffer
                env->ReleasePrimitiveArrayCritical(
                    reinterpret_cast<jarray>(byte_buffer_array.get()),
                    elements,
                    JNI_ABORT
                );
                JNIUlDelegatedBuffer::RELEASE.invoke(env, delegated_buffer);

                return ultralight_buffer;
            }

            DelegatedBufferArrayType array_type;
            if (!byte_buffer_has_array) {
                // We made an entirely new array to copy the data to
                array_type = DelegatedBufferArrayType::INDIRECT_COPY;
            } else if (elements_is_copy) {
                // We got a copy of the array, writes will only be visible to the JVM
                // once we commit the changes
                array_type = DelegatedBufferArrayType::DIRECT_COPY;
            } else {
                // We got a direct copy of the array, writes will be visible to the JVM
                // immediately
                array_type = DelegatedBufferArrayType::DIRECT_NO_COPY;
            }

            auto *holder = new DelegatedBufferHolder(
                delegated_buffer.clone_as_global(env),
                byte_buffer.clone_as_global(),
                byte_buffer_array.clone_as_global(),
                elements,
                array_type,
                buffer_length,
                byte_buffer_array_offset,
                buffer_position
            );

            // Create a buffer directly writing to the array
            ultralight_buffer = ultralight::Buffer::Create(
                elements + byte_buffer_array_offset,
                buffer_length,
                holder,
                DelegatedBufferHolder::delete_callback
            );

            return ultralight_buffer;
        }
    }
} // namespace ujr
