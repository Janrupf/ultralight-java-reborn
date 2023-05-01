#include "net_janrupf_ujr_platform_jni_impl_JNIUlPlatform_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_impl_JNIUlPlatformProvider.h"

#include <Ultralight/platform/Platform.h>

#include "ujr/Platform.hpp"
#include "ujr/util/JniEntryGuard.hpp"

// This method really does not more than pass through the call to the C++ API.
JNIEXPORT jobject JNICALL
Java_net_janrupf_ujr_platform_jni_impl_JNIUlPlatformProvider_nativeInstance(JNIEnv *env, jobject) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlPlatform;

        auto &platform = ultralight::Platform::instance();

        auto *collector = new ujr::PlatformCollector;

        // Allocate the object and set the native handle and collector
        auto j_platform = JNIUlPlatform::CLAZZ.alloc_object(env);
        JNIUlPlatform::HANDLE.set(env, j_platform, reinterpret_cast<jlong>(&platform));
        JNIUlPlatform::NATIVE_COLLECTOR.set(env, j_platform, reinterpret_cast<jlong>(collector));

        // Attach the collector to the platform
        ujr::GCSupport::attach_collector(env, j_platform, collector);

        return j_platform.leak();
    });
}
