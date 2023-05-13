#include "net_janrupf_ujr_platform_jni_impl_JNIUlPlatformProvider.h"

#include "ujr/Platform.hpp"
#include "ujr/util/JniEntryGuard.hpp"

// This method really does not more than pass through the call to the C++ API.
JNIEXPORT jobject JNICALL
Java_net_janrupf_ujr_platform_jni_impl_JNIUlPlatformProvider_nativeInstance(JNIEnv *env, jobject) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        auto &platform = ultralight::Platform::instance();
        auto wrapped = ujr::Platform::wrap(env, platform);

        return wrapped.leak();
    });
}
