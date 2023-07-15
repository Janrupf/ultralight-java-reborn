#pragma once

#include <JavaScriptCore/JSValueRef.h>

#include <vector>

#include "ujr/util/JniEnv.hpp"
#include "ujr/util/JniRef.hpp"

namespace ujr {
    /**
     * Helper class to provide static utility functions for JavaScript.
     */
    class JSUtil {
    public:
        explicit JSUtil() = delete;

        /**
         * Converts a java JSValue array to a vector of JSValueRef.
         *
         * @param env the JNI environment
         * @param array the java JSValue array
         * @return the vector of JSValueRef
         */
        static std::vector<JSValueRef> value_array_to_vector(const JniEnv &env, const JniStrongRef<jobjectArray> &array);
    };
} // namespace ujr
