#include "net_janrupf_ujr_api_logger_UltralightLogLevel_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_wrapper_logger_JNIUlLoggerNative.h"
#include "net_janrupf_ujr_platform_jni_wrapper_logger_JNIUlLoggerNative_native_access.hpp"

#include <Ultralight/platform/Logger.h>

#include "ujr/util/JniEntryGuard.hpp"
#include "ujr/util/JniEnv.hpp"
#include "ujr/util/JniRef.hpp"

namespace {
    ultralight::LogLevel translate_log_level(const ujr::JniEnv &env, const ujr::JniLocalRef<jobject> &j_log_level) {
        using ujr::native_access::UltralightLogLevel;

        if (env->IsSameObject(j_log_level, UltralightLogLevel::ERROR.get(env))) {
            return ultralight::LogLevel::Error;
        } else if (env->IsSameObject(j_log_level, UltralightLogLevel::WARNING.get(env))) {
            return ultralight::LogLevel::Warning;
        } else if (env->IsSameObject(j_log_level, UltralightLogLevel::INFO.get(env))) {
            return ultralight::LogLevel::Info;
        } else {
            throw std::runtime_error("Unknown log level");
        }
    }
} // namespace

JNIEXPORT void JNICALL Java_net_janrupf_ujr_platform_jni_wrapper_logger_JNIUlLoggerNative_nativeLogMessage(
    JNIEnv *env, jobject self, jobject log_level, jstring message
) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlLoggerNative;

        auto j_log_level = env.wrap_argument(log_level);
        auto j_message = env.wrap_argument(message);

        auto *logger = reinterpret_cast<ultralight::Logger *>(JNIUlLoggerNative::HANDLE.get(env, self));
        logger->LogMessage(translate_log_level(env, j_log_level), j_message.to_utf16());
    });
}
