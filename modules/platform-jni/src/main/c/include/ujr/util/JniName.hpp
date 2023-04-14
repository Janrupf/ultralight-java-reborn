#pragma once

#include <cstdint>

namespace ujr {
    /**
     * A compile time string literal for JNI names.
     *
     * @tparam N the length of the string literal excluding the null terminator
     */
    template<size_t N> struct JniName {
        char buffer[N + 1];

        /**
         * Constructs a new JNI member name.
         *
         * @param name the name of the member
         */
        constexpr JniName(const char *name) noexcept // NOLINT(google-explicit-constructor)
            : buffer() {
            for (size_t i = 0; i < N; i++) {
                buffer[i] = name[i];
            }
            buffer[N] = '\0';
        }

        constexpr operator const char *() const noexcept { // NOLINT(google-explicit-constructor)
            return buffer;
        }
    };

    /**
     * A compile time string literal for JNI class names.
     *
     * @tparam N the length of the string literal excluding the null terminator
     */
    template<size_t N> struct JniClassName : public JniName<N> {
        /**
         * Constructs a new JNI class name.
         *
         * @param name the name of the class
         */
        constexpr JniClassName(const char *name) noexcept // NOLINT(google-explicit-constructor)
            : JniName<N>(name) {}
    };

    template<size_t N> JniClassName(const char (&)[N]) -> JniClassName<N - 1>;

    /**
     * A compile time string literal for JNI method and field names.
     *
     * @tparam N the length of the string literal excluding the null terminator
     */
    template<size_t N> struct JniMemberName : public JniName<N> {
        /**
         * Constructs a new JNI member name.
         *
         * @param name the name of the member
         */
        constexpr JniMemberName(const char *name) noexcept // NOLINT(google-explicit-constructor)
            : JniName<N>(name) {}
    };

    template<size_t N> JniMemberName(const char (&)[N]) -> JniMemberName<N - 1>;
} // namespace ujr
