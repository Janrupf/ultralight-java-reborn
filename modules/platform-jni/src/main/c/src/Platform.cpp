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

        // Check that the config object is not null
        env.wrap_argument(config).require_non_null_argument("config");

        // Begin the translation
        ultralight::Config native_config;

        native_config.cache_path
            = UlConfig::CACHE_PATH.get(env, config).require_non_null_argument("config.cachePath").to_utf16();
        native_config.resource_path_prefix = UlConfig::RESOURCE_PATH_PREFIX.get(env, config)
                                                 .require_non_null_argument("config.resourcePathPrefix")
                                                 .to_utf16();
        UlConfig::FACE_WINDING.get(env, config);
    });
}
