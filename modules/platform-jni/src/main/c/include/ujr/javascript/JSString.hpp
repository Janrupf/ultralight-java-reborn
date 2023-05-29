#pragma once

#include <JavaScriptCore/JSStringRef.h>

#include "ujr/util/JniEnv.hpp"
#include "ujr/util/JniRef.hpp"

namespace ujr {
    class JSString {
    public:
        explicit JSString() = delete;

        /**
         * Converts a JavaScript string to a Java string.
         *
         * @param env the JNI environment to use
         * @param string the JavaScript string to convert
         * @return the Java string
         */
        static JniLocalRef<jstring> to_java(const JniEnv &env, JSStringRef string);

        /**
         * Converts a Java string to a JavaScript string.
         *
         * @param env the JNI environment to use
         * @param string the Java string to convert
         * @return the JavaScript string
         */
        static JSStringRef from_java(const JniEnv &env, const JniStrongRef<jstring> &string);
    };
} // namespace ujr
