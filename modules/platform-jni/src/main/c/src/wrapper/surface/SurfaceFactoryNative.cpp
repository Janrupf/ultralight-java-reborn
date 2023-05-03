#include "net_janrupf_ujr_platform_jni_wrapper_surface_JNIUlSurfaceFactoryNative.h"
#include "net_janrupf_ujr_platform_jni_wrapper_surface_JNIUlSurfaceFactoryNative_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_wrapper_surface_JNIUlSurfaceNative_native_access.hpp"

#include <Ultralight/platform/Surface.h>

#include "ujr/util/JniEntryGuard.hpp"

JNIEXPORT jobject JNICALL
Java_net_janrupf_ujr_platform_jni_wrapper_surface_JNIUlSurfaceFactoryNative_nativeCreateSurface(
    JNIEnv *env, jobject self, jlong width, jlong height
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlSurfaceFactoryNative;
        using ujr::native_access::JNIUlSurfaceNative;

        auto *surface
            = reinterpret_cast<ultralight::SurfaceFactory *>(JNIUlSurfaceFactoryNative::HANDLE.get(env, self));
        auto *ul_surface = surface->CreateSurface(width, height);

        auto j_ul_surface = JNIUlSurfaceNative::CLAZZ.alloc_object(env);
        JNIUlSurfaceNative::HANDLE.set(env, j_ul_surface, reinterpret_cast<jlong>(ul_surface));

        return j_ul_surface.leak();
    });
}

JNIEXPORT void JNICALL Java_net_janrupf_ujr_platform_jni_wrapper_surface_JNIUlSurfaceFactoryNative_nativeDestroySurface(
    JNIEnv *env, jobject self, jobject surface
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlSurfaceFactoryNative;
        using ujr::native_access::JNIUlSurfaceNative;

        auto j_ul_surface = env.wrap_argument(surface);
        auto *ul_surface = reinterpret_cast<ultralight::Surface *>(JNIUlSurfaceNative::HANDLE.get(env, j_ul_surface));
        JNIUlSurfaceNative::HANDLE.set(env, j_ul_surface, reinterpret_cast<jlong>(nullptr));

        auto *surface_factory
            = reinterpret_cast<ultralight::SurfaceFactory *>(JNIUlSurfaceFactoryNative::HANDLE.get(env, self));
        surface_factory->DestroySurface(ul_surface);
    });
}
