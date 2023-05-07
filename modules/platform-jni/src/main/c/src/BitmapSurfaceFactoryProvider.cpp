#include "net_janrupf_ujr_platform_jni_impl_JNIUlBitmapSurfaceFactoryProvider.h"
#include "net_janrupf_ujr_platform_jni_wrapper_surface_JNIUlSurfaceFactoryNative_native_access.hpp"

#include <Ultralight/platform/Surface.h>

#include "ujr/util/JniEntryGuard.hpp"

JNIEXPORT jobject JNICALL
Java_net_janrupf_ujr_platform_jni_impl_JNIUlBitmapSurfaceFactoryProvider_nativeGetBitmapSurfaceFactory(
    JNIEnv *env, jobject self
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlSurfaceFactoryNative;

        auto *factory = ultralight::GetBitmapSurfaceFactory();

        auto j_factory = JNIUlSurfaceFactoryNative::CLAZZ.alloc_object(env);
        JNIUlSurfaceFactoryNative::HANDLE.set(env, j_factory, reinterpret_cast<jlong>(factory));

        return j_factory.leak();
    });
}
