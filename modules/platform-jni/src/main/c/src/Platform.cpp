#include "net_janrupf_ujr_core_platform_abstraction_config_UlConfig_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_impl_JNIUlPlatform.h"

#include <Ultralight/platform/Config.h>

#include "ujr/util/JniClass.hpp"
#include "ujr/util/JniEntryGuard.hpp"
#include "ujr/util/JniField.hpp"

JNIEXPORT void JNICALL
Java_net_janrupf_ujr_platform_jni_impl_JNIUlPlatform_nativeSetConfig(JNIEnv *env, jobject self, jobject config) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::UlConfig;

        ultralight::Config config;

        config.cache_path = UlConfig::CACHE_PATH.get(env, self).to_utf16();
        config.resource_path_prefix = UlConfig::RESOURCE_PATH_PREFIX.get(env, self).to_utf16();
        UlConfig::FACE_WINDING.get(env, self);
    });
}
