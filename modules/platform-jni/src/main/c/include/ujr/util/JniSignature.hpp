#pragma once

#include "ujr/util/JniType.hpp"

namespace ujr {
    namespace _internal {
        /**
         * Compile time string instance
         *
         * @tparam N the length of the string literal excluding the null terminator
         */
        template<size_t N> struct CompileTimeString {
            char buffer[N + 1];

            /**
             * Constructs a new compile time string instance.
             *
             * @param s the string literal
             */
            constexpr CompileTimeString(const char s[N + 1]) // NOLINT(google-explicit-constructor)
                : buffer() {
                for (size_t i = 0; i < N; i++) {
                    buffer[i] = s[i];
                }
                buffer[N] = '\0';
            }

            /**
             * Converts the compile time string to a class name.
             *
             * @return the class name
             */
            [[nodiscard]] constexpr JniClassName<N> as_class_name() const noexcept { return JniClassName(buffer); }

            /**
             * Converts the compile time string to a C string.
             *
             * @return the C string
             */
            constexpr operator const char *() const noexcept { // NOLINT(google-explicit-constructor)
                return buffer;
            }
        };

        template<size_t N> CompileTimeString(const char (&)[N]) -> CompileTimeString<N - 1>;

        /**
         * Concatenates compile time strings.
         *
         * This is the overload for no strings and returns a string of length 0.
         *
         * @return a string of length 0
         */
        constexpr CompileTimeString<0> concat_compile_time_strings() { return { "" }; }

        /**
         * Concatenates compile time strings.
         *
         * This is the overload for a single string.
         *
         * @tparam A the length of the string literal excluding the null terminator
         * @param a the string literal
         * @return a
         */
        template<size_t A> constexpr CompileTimeString<A> concat_compile_time_strings(CompileTimeString<A> a) {
            return a;
        }

        /**
         * Concatenates compile time strings.
         *
         * @tparam A the length of string literal a excluding the null terminator
         * @tparam B the length of string literal b excluding the null terminator
         * @param a string literal a
         * @param b string literal b
         * @return a string literal concatenating a and b of length A + B
         */
        template<size_t A, size_t B>
        constexpr CompileTimeString<A + B> concat_compile_time_strings(CompileTimeString<A> a, CompileTimeString<B> b) {
            char buffer[A + B + 1];

            for (size_t i = 0; i < A; i++) {
                buffer[i] = a.buffer[i];
            }

            for (size_t i = 0; i < B; i++) {
                buffer[A + i] = b.buffer[i];
            }

            buffer[A + B] = '\0';

            return CompileTimeString(buffer);
        }

        /**
         * Concatenates compile time strings.
         *
         * @tparam T the type of first string literal
         * @tparam Args the type of remaining string literals
         * @param current the first string literal
         * @param strings the remaining string literals
         * @return a string literal concatenating all string literals
         */
        template<typename T, typename... Args> constexpr auto concat_compile_time_strings(T current, Args... strings) {
            return concat_compile_time_strings(current, concat_compile_time_strings(strings...));
        }

        /**
         * Converts a JNI type name to a compile time string.
         *
         * @tparam N the length of the string literal excluding the null terminator
         * @param name the JNI type name
         * @return the JNI type name as a compile time string
         */
        template<size_t N> constexpr CompileTimeString<N> jni_type_name(JniClassName<N> name) {
            return CompileTimeString<N>(name.buffer);
        }
    } // namespace _internal

    /**
     * Helper struct for constructing JNI method signatures.
     *
     * @tparam R the return type of the signature
     * @tparam Args the argument types of the signature
     */
    template<typename R, typename... Args>
        requires(IsJniType<R> && (IsJniType<Args> && ...))
    struct JniMethodSignature {
        static constexpr _internal::CompileTimeString Signature = _internal::concat_compile_time_strings(
            _internal::CompileTimeString("("),
            _internal::concat_compile_time_strings(_internal::jni_type_name(JniType<Args>::Name)...),
            _internal::CompileTimeString(")"),
            _internal::jni_type_name(JniType<R>::Name)
        );
    };

    /**
     * Helper struct for constructing JNI class signatures.
     *
     * @tparam T the JNI class name
     */
    template<JniClassName T> struct JniClassSignature {
        static constexpr JniClassName Signature
            = _internal::concat_compile_time_strings(
                  _internal::CompileTimeString("L"), _internal::jni_type_name(T), _internal::CompileTimeString(";")
            )
                  .as_class_name();
    };
} // namespace ujr
