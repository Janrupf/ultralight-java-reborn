#include "net_janrupf_ujr_platform_jni_wrapper_buffer_JNIUlBufferNative.h"
#include "net_janrupf_ujr_platform_jni_wrapper_buffer_JNIUlBufferNative_native_access.hpp"

#include <Ultralight/Buffer.h>

#include "ujr/util/JniEntryGuard.hpp"
#include "ujr/wrapper/buffer/BufferNative.hpp"

JNIEXPORT jobject JNICALL
Java_net_janrupf_ujr_platform_jni_wrapper_buffer_JNIUlBufferNative_nativeAsByteBuffer(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlBufferNative;

        auto *buffer = reinterpret_cast<ultralight::Buffer *>(JNIUlBufferNative::HANDLE.get(env, self));
        auto *data = buffer->data();
        auto size = buffer->size();

        return env->NewDirectByteBuffer(data, size);
    });
}

JNIEXPORT void JNICALL
Java_net_janrupf_ujr_platform_jni_wrapper_buffer_JNIUlBufferNative_nativeClose(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlBufferNative;

        auto *buffer = reinterpret_cast<ultralight::Buffer *>(JNIUlBufferNative::HANDLE.get(env, self));
        buffer->Release();
    });
}

namespace ujr {
    JniLocalRef<jobject>
    BufferNative::as_java_buffer(const JniEnv &env, ultralight::RefPtr<ultralight::Buffer> buffer) {
        using native_access::JNIUlBufferNative;

        // We will take over reference counting ourselves
        auto *buffer_ref = buffer.LeakRef();

        // Allocate the java object and set the handle
        auto obj = JNIUlBufferNative::CLAZZ.alloc_object(env);
        JNIUlBufferNative::HANDLE.set(env, obj, reinterpret_cast<jlong>(buffer_ref));

        return obj;
    }
} // namespace ujr