#include "net_janrupf_ujr_platform_jni_impl_JNIUlPlatformProvider.h"

#include <Ultralight/platform/Platform.h>

#include "ujr/util/JniEntryGuard.hpp"

// This method really does not more than pass through the call to the C++ API.
JNIEXPORT jlong JNICALL
Java_net_janrupf_ujr_platform_jni_impl_JNIUlPlatformProvider_nativeInstance(JNIEnv *env, jobject) {
    return ujr::jni_entry_guard(env, [&]([[maybe_unused]] auto env) {
        auto &platform = ultralight::Platform::instance();
        return reinterpret_cast<jlong>(&platform);
    });
}
