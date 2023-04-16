#pragma once

#include <jni.h>

#include "ujr/util/JniEnv.hpp"
#include "ujr/util/JniException.hpp"

namespace ujr {
    /**
     * Invokes a C++ callback and guards the environment against C++ exceptions propagating to Java.
     *
     * @tparam Cb the callback type
     * @param env the JNI environment to use
     * @param cb the callback to invoke
     */
    template<typename Cb>
    void jni_entry_guard(JNIEnv *env, Cb &&cb)
        requires(std::is_invocable_v<Cb, const JniEnv &> && std::is_same_v<std::invoke_result_t<Cb, JniEnv>, void>)
    {
        auto c_env = JniEnv::from_existing(env);
        try {
            cb(c_env);
        } catch (...) {
            auto exception = JniException::from_cpp(std::current_exception());

            if (!exception.rethrow(c_env)) {
                // If the exception could not be rethrown, we have to abort the program
                std::abort();
            }
        }
    }

    /**
     * Invokes a C++ callback and guards the environment against C++ exceptions propagating to Java.
     *
     * @tparam Cb the callback type
     * @param env the JNI environment to use
     * @param cb the callback to invoke
     * @return the result of the callback
     */
    template<typename Cb>
    std::invoke_result_t<Cb, JniEnv> jni_entry_guard(JNIEnv *env, Cb &&cb)
        requires(std::is_invocable_v<Cb, const JniEnv &> && !std::is_same_v<std::invoke_result_t<Cb, JniEnv>, void>)
    {
        auto c_env = JniEnv::from_existing(env);
        try {
            return std::forward<std::invoke_result_t<Cb, JniEnv>>(cb(c_env));
        } catch (...) {
            auto exception = JniException::from_cpp(std::current_exception());

            if (!exception.rethrow(c_env)) {
                // If the exception could not be rethrown, we have to abort the program
                std::abort();
            }

            return {};
        }
    }
} // namespace ujr
