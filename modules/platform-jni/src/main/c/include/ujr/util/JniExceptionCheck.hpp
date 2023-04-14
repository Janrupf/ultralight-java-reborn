#pragma once

#include "ujr/util/JniEnv.hpp"

namespace ujr {
    /**
     * Helper class to check for a pending JNI exception.
     *
     * This class is used to avoid having to include JniException.hpp due to circular dependencies.
     */
    class JniExceptionCheck {
    public:
        explicit JniExceptionCheck() = delete;

        /**
         * Alias for JniException::throw_if_pending.
         *
         * @param env the JNI environment to check for a pending exception
         */
        static void throw_if_pending(const JniEnv &env);
    };
}
