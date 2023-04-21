#pragma once

#include <Ultralight/platform/Logger.h>

#include "ujr/util/JniRef.hpp"

namespace ujr {
    /**
     * Logger adapter for Ultralight delegating to a Java instance.
     */
    class Logger : public ultralight::Logger {
    private:
        JniGlobalRef<jobject> j_logger;

    public:
        explicit Logger(JniGlobalRef<jobject> j_logger);

        void LogMessage(ultralight::LogLevel log_level, const ultralight::String &message) final;

        /**
         * Retrieves the underlying java logger instance.
         *
         * @return the java logger instance
         */
        [[nodiscard]] const JniGlobalRef<jobject> &get_j_logger() const;
    };
} // namespace ujr
