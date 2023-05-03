#include "net_janrupf_ujr_api_math_IntRect_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_wrapper_surface_JNIUlSurfaceNative.h"
#include "net_janrupf_ujr_platform_jni_wrapper_surface_JNIUlSurfaceNative_native_access.hpp"

#include <Ultralight/platform/Surface.h>

#include "ujr/util/JniEntryGuard.hpp"
#include "ujr/util/JniRef.hpp"

JNIEXPORT jlong JNICALL
Java_net_janrupf_ujr_platform_jni_wrapper_surface_JNIUlSurfaceNative_nativeWidth(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlSurfaceNative;

        auto *ul_surface = reinterpret_cast<ultralight::Surface *>(JNIUlSurfaceNative::HANDLE.get(env, self));
        return static_cast<jlong>(ul_surface->width());
    });
}

JNIEXPORT jlong JNICALL
Java_net_janrupf_ujr_platform_jni_wrapper_surface_JNIUlSurfaceNative_nativeHeight(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlSurfaceNative;

        auto *ul_surface = reinterpret_cast<ultralight::Surface *>(JNIUlSurfaceNative::HANDLE.get(env, self));
        return static_cast<jlong>(ul_surface->height());
    });
}

JNIEXPORT jlong JNICALL
Java_net_janrupf_ujr_platform_jni_wrapper_surface_JNIUlSurfaceNative_nativeRowBytes(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlSurfaceNative;

        auto *ul_surface = reinterpret_cast<ultralight::Surface *>(JNIUlSurfaceNative::HANDLE.get(env, self));
        return static_cast<jlong>(ul_surface->row_bytes());
    });
}

JNIEXPORT jlong JNICALL
Java_net_janrupf_ujr_platform_jni_wrapper_surface_JNIUlSurfaceNative_nativeSize(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlSurfaceNative;

        auto *ul_surface = reinterpret_cast<ultralight::Surface *>(JNIUlSurfaceNative::HANDLE.get(env, self));
        return static_cast<jlong>(ul_surface->size());
    });
}

JNIEXPORT jobject JNICALL
Java_net_janrupf_ujr_platform_jni_wrapper_surface_JNIUlSurfaceNative_nativeLockPixels(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlSurfaceNative;

        auto *ul_surface = reinterpret_cast<ultralight::Surface *>(JNIUlSurfaceNative::HANDLE.get(env, self));
        auto *pixels = ul_surface->LockPixels();

        auto direct_buffer = env->NewDirectByteBuffer(pixels, ul_surface->size());
        if (direct_buffer || env->ExceptionCheck()) {
            return direct_buffer;
        }

        // Direct buffers not supported, use a byte array
        auto indirect_array = ujr::JniLocalRef<jbyteArray>::wrap(env, env->NewByteArray(ul_surface->size()));
        if (!indirect_array.is_valid() || env->ExceptionCheck()) {
            return static_cast<jobject>(indirect_array.leak());
        }

        // Copy the data from the pixel buffer to the array and store the original pointer
        env->SetByteArrayRegion(indirect_array.get(), 0, ul_surface->size(), reinterpret_cast<jbyte *>(pixels));
        JNIUlSurfaceNative::LOCKED_PIXELS.set(env, self, reinterpret_cast<jlong>(pixels));

        return static_cast<jobject>(indirect_array.leak());
    });
}

JNIEXPORT void JNICALL Java_net_janrupf_ujr_platform_jni_wrapper_surface_JNIUlSurfaceNative_nativeUnlockPixels(
    JNIEnv *env, jobject self, jbyteArray storage
) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlSurfaceNative;

        auto j_storage = env.wrap_argument(storage);
        if (j_storage.is_valid()) {
            // Copy back the array to the locked pixels
            auto *pixels = reinterpret_cast<jbyte *>(JNIUlSurfaceNative::LOCKED_PIXELS.get(env, self));
            env->GetByteArrayRegion(j_storage.get(), 0, env->GetArrayLength(j_storage.get()), pixels);

            JNIUlSurfaceNative::LOCKED_PIXELS.set(env, self, reinterpret_cast<jlong>(nullptr));
        }

        auto *ul_surface = reinterpret_cast<ultralight::Surface *>(JNIUlSurfaceNative::HANDLE.get(env, self));
        ul_surface->UnlockPixels();
    });
}

JNIEXPORT void JNICALL Java_net_janrupf_ujr_platform_jni_wrapper_surface_JNIUlSurfaceNative_nativeResize(
    JNIEnv *env, jobject self, jlong width, jlong height
) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlSurfaceNative;

        auto *ul_surface = reinterpret_cast<ultralight::Surface *>(JNIUlSurfaceNative::HANDLE.get(env, self));
        ul_surface->Resize(width, height);
    });
}

JNIEXPORT void JNICALL Java_net_janrupf_ujr_platform_jni_wrapper_surface_JNIUlSurfaceNative_nativeSetDirtyBounds(
    JNIEnv *env, jobject self, jobject dirty_bounds
) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlSurfaceNative;
        using ujr::native_access::IntRect;

        auto j_dirty_bounds = env.wrap_argument(dirty_bounds);

        auto *ul_surface = reinterpret_cast<ultralight::Surface *>(JNIUlSurfaceNative::HANDLE.get(env, self));
        ultralight::IntRect ul_dirty_bounds { IntRect::LEFT.get(env, j_dirty_bounds),
                                              IntRect::TOP.get(env, j_dirty_bounds),
                                              IntRect::RIGHT.get(env, j_dirty_bounds),
                                              IntRect::BOTTOM.get(env, j_dirty_bounds) };

        ul_surface->set_dirty_bounds(ul_dirty_bounds);
    });
}

JNIEXPORT jobject JNICALL
Java_net_janrupf_ujr_platform_jni_wrapper_surface_JNIUlSurfaceNative_nativeDirtyBounds(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlSurfaceNative;
        using ujr::native_access::IntRect;

        auto *ul_surface = reinterpret_cast<ultralight::Surface *>(JNIUlSurfaceNative::HANDLE.get(env, self));
        auto ul_dirty_bounds = ul_surface->dirty_bounds();

        auto j_dirty_bounds = IntRect::CLAZZ.alloc_object(env);
        IntRect::LEFT.set(env, j_dirty_bounds, ul_dirty_bounds.left);
        IntRect::TOP.set(env, j_dirty_bounds, ul_dirty_bounds.top);
        IntRect::RIGHT.set(env, j_dirty_bounds, ul_dirty_bounds.right);
        IntRect::BOTTOM.set(env, j_dirty_bounds, ul_dirty_bounds.bottom);

        return j_dirty_bounds.leak();
    });
}

JNIEXPORT void JNICALL
Java_net_janrupf_ujr_platform_jni_wrapper_surface_JNIUlSurfaceNative_nativeClearDirtyBounds(JNIEnv *env, jobject self) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlSurfaceNative;

        auto *ul_surface = reinterpret_cast<ultralight::Surface *>(JNIUlSurfaceNative::HANDLE.get(env, self));
        ul_surface->ClearDirtyBounds();
    });
}
