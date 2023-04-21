#include "ujr/wrapper/logger/Logger.hpp"
#include "net_janrupf_ujr_api_logger_UltralightLogLevel_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_wrapper_logger_JNIUlLogger_native_access.hpp"

#include <stdexcept>

#include "ujr/util/JniEnv.hpp"

namespace ujr {
    namespace {
        ujr::JniLocalRef<jobject> translate_log_level(const JniEnv &env, ultralight::LogLevel level) {
            switch (level) {
                case ultralight::LogLevel::Error:
                    return native_access::UltralightLogLevel::ERROR.get(env);

                case ultralight::LogLevel::Warning:
                    return native_access::UltralightLogLevel::WARNING.get(env);

                case ultralight::LogLevel::Info:
                    return native_access::UltralightLogLevel::INFO.get(env);

                default:
                    throw std::runtime_error("Unknown log level");
            }
        }
    } // namespace

    Logger::Logger(JniGlobalRef<jobject> j_logger)
        : j_logger(std::move(j_logger)) {}

    void Logger::LogMessage(ultralight::LogLevel log_level, const ultralight::String &message) {
        auto env = JniEnv::require_existing_from_thread();

        // Translate the log level and message
        auto j_log_level = translate_log_level(env, log_level);
        auto j_message = JniLocalRef<jstring>::from_utf16(env, message.utf16());

        // Call the Java instance
        native_access::JNIUlLogger::LOG_MESSAGE.invoke(env, j_logger, j_log_level, j_message);
    }

    const JniGlobalRef<jobject> &Logger::get_j_logger() const { return j_logger; }
} // namespace ujr