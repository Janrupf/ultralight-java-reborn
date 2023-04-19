#pragma once

#include <string>

#include "ujr/util/JniEnv.hpp"

namespace ujr {
    /**
     * Helper class to check for a pending JNI exception and construct other JNI exceptions.
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

        /**
         * Throws a JniIllegalArgumentException.
         *
         * @param argument the argument that is illegal
         * @param message a message describing why the argument is illegal
         */
        static void throw_illegal_argument(std::string argument, std::string message);
    };
} // namespace ujr
