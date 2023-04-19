#pragma once

#include <jni.h>

#include <exception>
#include <variant>

#include "ujr/util/JniEnv.hpp"
#include "ujr/util/JniRef.hpp"

namespace ujr {
    /**
     * Helper class to deal with exceptions either thrown by C++ or Java.
     */
    class JniException : public std::exception {
    private:
        // The underlying exception
        std::variant<JniLocalRef<jthrowable>, std::exception_ptr> exception;

    protected:
        /**
         * Constructs a new JniException.
         *
         * The resulting exception will not be valid unless the `translate_to_java` method is
         * overridden.
         */
        explicit JniException();

        /**
         * Constructs a new JniException from an existing Java or C++ exception.
         *
         * @param exception the existing exception
         */
        explicit JniException(std::variant<JniLocalRef<jthrowable>, std::exception_ptr> exception);

    public:
        JniException(const JniException &other) = delete;
        JniException(JniException &&other) noexcept;

        JniException &operator=(const JniException &other) = delete;
        JniException &operator=(JniException &&other) noexcept;

        /**
         * Rethrows this C++ exceptions as a JNI exception.
         *
         * @param env the JNI environment to rethrow the exception in
         * @return true if the exception could be rethrown, false otherwise
         */
        [[nodiscard]] bool rethrow(const JniEnv &env) const;

        /**
         * Translates this exception to a Java exception.
         *
         * @param env the JNI environment to use
         * @return the exception as a Java exception
         */
        [[nodiscard]] virtual JniLocalRef<jthrowable> translate_to_java() const;

        /**
         * Throws a C++ exception if a JNI exception is pending.
         *
         * @param env the JNI environment to check for a pending exception
         */
        static void throw_if_pending(const JniEnv &env);

        /**
         * Throws a Java exception as a C++ exception.
         *
         * @param exception the exception to throw
         */
        [[noreturn]] static void throw_java_as_cpp(JniLocalRef<jthrowable> exception);

        /**
         * Constructs a JNI exception from a C++ exception.
         *
         * @param exception the C++ exception to construct the JNI exception from
         * @return the JNI exception
         */
        [[nodiscard]] static JniException from_cpp(std::exception_ptr exception);

        /**
         * Constructs a JNI exception from a Java exception.
         *
         * @param exception the Java exception to construct the JNI exception from
         * @return the JNI exception
         */
        [[nodiscard]] static JniException from_java(JniLocalRef<jthrowable> exception);
    };
} // namespace ujr
