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

        /**
         * Converts a java JSPropertyAttribute array to a JSPropertyAttributes bit mask.
         *
         * @param env the JNI environment
         * @param attributes the java JSPropertyAttribute array
         * @return the JSPropertyAttributes bit mask
         */
        static unsigned property_attributes_to_js(const JniEnv &env, const JniStrongRef<jobjectArray> &attributes);

        /**
         * Converts a java JSClassAttribute array to a JSClassAttributes bit mask.
         *
         * @param env the JNI environment
         * @param attributes the java JSClassAttribute array
         * @return the JSClassAttributes bit mask
         */
        static unsigned class_attributes_to_js(const JniEnv &env, const JniStrongRef<jobjectArray> &attributes);
    };
} // namespace ujr
